package com.saschaw.hooked.core.datastore

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.saschaw.hooked.core.model.UserData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import net.openid.appauth.AuthState
import javax.inject.Inject


val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_data")

class HookedPreferencesDataSource @Inject constructor(
    context: Context
) {
    private val preferences = context.dataStore
    private val userDataKey = stringPreferencesKey("user_data")

    fun getAuthState(): Flow<AuthState?> = getUserData().map { it?.authState }

    suspend fun updateAuthState(authState: AuthState?) = updateUserData(authState = authState)

    fun getShouldShowOnboarding(): Flow<Boolean> =
        getUserData().map { it?.shouldShowOnboarding ?: true }

    suspend fun updateShouldShowOnboarding(shouldShowOnboarding: Boolean) =
        updateUserData(shouldShowOnboarding = shouldShowOnboarding)


    private fun getUserData() = preferences.data.map { preferences ->
        preferences[userDataKey]?.let {
            try {
                Json.decodeFromString<UserData>(it)
            } catch (e: Exception) {
                Log.e("HookedPrefsDataSource", "Error getting user data", e)
                null
            }
        }
    }

    private suspend fun updateUserData(
        authState: AuthState? = null,
        shouldShowOnboarding: Boolean? = null
    ) {
        getUserData().collectLatest {
            it?.let {
                val newData = it.copy(
                    authState = authState ?: it.authState,
                    shouldShowOnboarding = shouldShowOnboarding ?: it.shouldShowOnboarding
                )

                preferences.edit { mutablePreferences ->
                    mutablePreferences[userDataKey] = Json.encodeToString(newData)
                }
            }
        }
    }
}