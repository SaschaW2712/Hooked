package com.saschaw.hooked.core.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PatternListItem(
    val designer: PatternAuthor? = null,
    val id: Int,
    val name: String,
    @SerialName("first_photo") val firstPhoto: RavelryPhoto? = null,
    @SerialName("pattern_author") val patternAuthor: PatternAuthor? = null,
    @SerialName("pattern_sources") val patternSources: List<PatternSource>? = emptyList(),
    val permalink: String
)


@Serializable
data class PatternSource(
    val author: String? = null,
    val id: Int,
    val name: String,
    val permalink: String
)