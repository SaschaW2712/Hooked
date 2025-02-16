package com.saschaw.hooked.core.model.components

import kotlinx.serialization.Serializable

@Serializable
data class PatternCategory(
    val id: Int,
    val name: String,
    val parent: PatternCategory? = null,
    val permalink: String
)
