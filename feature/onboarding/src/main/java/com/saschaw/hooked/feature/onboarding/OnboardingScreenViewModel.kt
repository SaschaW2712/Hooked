package com.saschaw.hooked.feature.onboarding

import androidx.lifecycle.ViewModel
import com.saschaw.hooked.core.authentication.AuthenticationManager
import com.saschaw.hooked.core.datastore.PreferencesDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.prefs.Preferences
import javax.inject.Inject

@HiltViewModel
class OnboardingScreenViewModel @Inject constructor(
    val authenticationManager: AuthenticationManager,
    private val preferences: PreferencesDataSource,
) : ViewModel() {

    suspend fun dismissOnboarding() {
        preferences.updateAppUserData(hasSeenOnboarding = true)
    }
}