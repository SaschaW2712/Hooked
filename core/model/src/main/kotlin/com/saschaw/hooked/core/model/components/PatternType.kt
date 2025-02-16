package com.saschaw.hooked.core.model.components

import kotlinx.serialization.Serializable

@Serializable
data class PatternType(
    val clothing: Boolean,
    val id: Int,
    val name: String,
    val permalink: String
)