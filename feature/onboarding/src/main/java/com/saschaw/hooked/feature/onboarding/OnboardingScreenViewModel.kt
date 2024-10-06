package com.saschaw.hooked.feature.onboarding

import androidx.lifecycle.ViewModel
import com.saschaw.hooked.core.authentication.AuthenticationManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OnboardingScreenViewModel @Inject constructor(
    val authenticationManager: AuthenticationManager
) : ViewModel()
