package com.saschaw.hooked.core.model.user

import kotlinx.serialization.Serializable

@Serializable
data class HookedAppUserData(
    val hasSeenOnboarding: Boolean = false,
    val username: String? = "",
)