package com.saschaw.hooked.core.model.components

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Printing(
    @SerialName("created_at") val creationDateString: String?,
    val id: Int,
    @SerialName("pattern_id") val patternId: Int?,
    @SerialName("pattern_not_available") val patternNotAvailable: Boolean?,
    @SerialName("pattern_source") val patternSource: PatternSource?,
    @SerialName("primary_source") val isPrimarySource: Boolean?,
)
