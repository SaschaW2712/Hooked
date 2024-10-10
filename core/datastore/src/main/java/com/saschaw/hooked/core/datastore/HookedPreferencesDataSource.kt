package com.saschaw.hooked.core.datastore

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.saschaw.hooked.core.model.HookedUserData
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import net.openid.appauth.AuthState
import javax.inject.Inject

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_data")

interface PreferencesDataSource {
    suspend fun initUserData()

    fun getAuthState(): Flow<AuthState?>
    suspend fun updateAuthState(authState: AuthState?)

    fun getUserData(): Flow<HookedUserData?>

    fun getHasSeenOnboarding(): Flow<Boolean>
    suspend fun updateHasSeenOnboarding(hasSeenOnboarding: Boolean)
}

class HookedPreferencesDataSource @Inject constructor(
    @ApplicationContext context: Context
): PreferencesDataSource {
    private val preferences = context.dataStore
    private val userDataKey = stringPreferencesKey("user_data_json")
    private val authStateKey = stringPreferencesKey("auth_state_json")

    override suspend fun initUserData() {
        preferences.edit {
            if (it[userDataKey] == null) {
                it[userDataKey] = Json.encodeToString(HookedUserData(false))
            }
        }
    }

    override fun getAuthState(): Flow<AuthState?> = preferences.data.map { preferences ->
        val authStatePref = preferences[authStateKey]
        if (authStatePref.isNullOrEmpty()) {
            return@map null
        }

        try {
            AuthState.jsonDeserialize(authStatePref)
        } catch (e: Exception) {
            Log.e("HookedPrefsDataSource", "Error getting auth state", e)
            null
        }
    }


    override suspend fun updateAuthState(authState: AuthState?) {
        preferences.edit { mutablePrefs ->
            val newValue = authState?.jsonSerializeString() ?: ""

            mutablePrefs[authStateKey] = newValue
        }
    }

    override fun getHasSeenOnboarding(): Flow<Boolean> =
        getUserData().map { it?.hasSeenOnboarding ?: false }

    override suspend fun updateHasSeenOnboarding(hasSeenOnboarding: Boolean) =
        updateUserData(hasSeenOnboarding = hasSeenOnboarding)

    override fun getUserData() = preferences.data.map { preferences ->
        preferences[userDataKey]?.let {
            try {
                Json.decodeFromString<HookedUserData>(it)
            } catch (e: Exception) {
                Log.e("HookedPrefsDataSource", "Error getting user data", e)
                null
            }
        }
    }

    private suspend fun updateUserData(
        hasSeenOnboarding: Boolean? = null
    ) {
        preferences.edit { mutablePrefs ->
            val userData = mutablePrefs[userDataKey]?.let { Json.decodeFromString<HookedUserData>(it) }

            userData?.let {
                val newData = it.copy(
                    hasSeenOnboarding = hasSeenOnboarding ?: userData.hasSeenOnboarding
                )

                mutablePrefs[userDataKey] = Json.encodeToString(newData)
            }
        }
    }
}