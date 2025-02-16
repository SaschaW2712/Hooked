package com.saschaw.hooked.core.model.user

import kotlinx.serialization.Serializable

@Serializable
data class RavelryUser(
    val id: Int,
    val username: String,
)