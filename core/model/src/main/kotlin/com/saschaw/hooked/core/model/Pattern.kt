package com.saschaw.hooked.core.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Pattern(
    val designer: PatternAuthor,
    val id: Int,
    val name: String,
    @SerialName("pattern_author") val patternAuthor: PatternAuthor,
    @SerialName("pattern_sources") val patternSources: List<PatternSource>,
    val permalink: String
)

@Serializable
data class PatternAuthor(
    val id: Int,
    val name: String,
    val permalink: String,
)

@Serializable
data class PatternSource(
    val author: String,
    val id: Int,
    val name: String,
    val permalink: String
)