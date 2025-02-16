package com.saschaw.hooked.core.model.components

import kotlinx.serialization.Serializable

@Serializable
data class PatternAttribute(
    val id: Int,
    val permalink: String
)