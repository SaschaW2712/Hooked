package com.saschaw.hooked.core.model

import kotlinx.serialization.Serializable

@Serializable
data class HookedUserData(
    val hasSeenOnboarding: Boolean = false,
)