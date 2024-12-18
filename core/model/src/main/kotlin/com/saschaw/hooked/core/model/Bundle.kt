package com.saschaw.hooked.core.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Bundle(
    @SerialName("bundle_cover") val bundleCover: RavelryPhoto?,
    @SerialName("bundled_items_count") val bundledItemsCount: Int?,
    @SerialName("first_photo") val firstPhoto: RavelryPhoto?,
    val id: Int,
    val name: String,
    val notes: String?,
    val user: RavelryPatternUser,
)