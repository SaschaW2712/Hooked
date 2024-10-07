package com.saschaw.hooked.core.model

import net.openid.appauth.AuthState

data class UserData(
    val authState: AuthState?,
    val shouldShowOnboarding: Boolean = false,
)
