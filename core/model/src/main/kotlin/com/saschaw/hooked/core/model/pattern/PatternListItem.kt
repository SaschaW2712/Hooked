package com.saschaw.hooked.core.model.pattern

import com.saschaw.hooked.core.model.components.PatternSource
import com.saschaw.hooked.core.model.components.RavelryPhoto
import com.saschaw.hooked.core.model.user.PatternAuthor
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