package com.saschaw.hooked.core.model.user

import kotlinx.serialization.Serializable


@Serializable
data class PatternAuthor(
    val id: Int,
    val name: String,
    val permalink: String,
    val users: List<RavelryPatternUser>,
)