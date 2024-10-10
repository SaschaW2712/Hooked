package com.saschaw.hooked.core.model

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: Int,
    val username: String,
)