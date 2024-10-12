package com.saschaw.hooked.core.datastore

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.saschaw.hooked.core.model.HookedAppUserData
import com.saschaw.hooked.core.model.RavelryUser
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

    suspend fun updateAppUserData(hasSeenOnboarding: Boolean? = null, username: String? = null)

    fun getHasSeenOnboarding(): Flow<Boolean>
    fun getRavelryUsername(): Flow<String?>
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
                it[userDataKey] = Json.encodeToString(HookedAppUserData(false, ""))
            }
        }
    }

    override fun getAuthState(): Flow<AuthState?> = preferences.data.map { preferences ->
        val authState = preferences[authStateKey]

        return@map if (authState.isNullOrEmpty()) {
            null
        } else {
            try {
                AuthState.jsonDeserialize(authState)
            } catch (e: Exception) {
                Log.e("HookedPrefsDataSource", "Error deserializing auth state", e)
                null
            }
        }
    }

    override suspend fun updateAuthState(authState: AuthState?) {
        preferences.edit { mutablePrefs ->
            val newValue = authState?.jsonSerializeString() ?: ""

            mutablePrefs[authStateKey] = newValue
        }

        if (authState == null) {
            updateAppUserData(username = null)
        }
    }

    override fun getHasSeenOnboarding(): Flow<Boolean> =
        getAppUserData().map { it?.hasSeenOnboarding ?: false }

    override fun getRavelryUsername(): Flow<String?> =
        getAppUserData().map { it?.username }


    private fun getAppUserData() = preferences.data.map { preferences ->
        preferences[userDataKey]?.let {
            try {
                Json.decodeFromString<HookedAppUserData>(it)
            } catch (e: Exception) {
                Log.e("HookedPrefsDataSource", "Error deserializing user data", e)
                null
            }
        }
    }

    override suspend fun updateAppUserData(
        hasSeenOnboarding: Boolean?,
        username: String?
    ) {
        try {
            preferences.edit { mutablePrefs ->
                val userData =
                    mutablePrefs[userDataKey]?.let { Json.decodeFromString<HookedAppUserData>(it) }

                userData?.let {
                    val newData = it.copy(
                        hasSeenOnboarding = hasSeenOnboarding ?: userData.hasSeenOnboarding,
                        username = when {
                            username != null -> username
                            !it.username.isNullOrEmpty() -> it.username
                            else -> ""
                        }
                    )

                    mutablePrefs[userDataKey] = Json.encodeToString(newData)
                }
            }
        } catch (e: Exception) {
            Log.e("HookedPrefsDataSource", "Error updating user data", e)
        }
    }
}