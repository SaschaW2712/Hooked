package com.saschaw.hooked.core.model.components

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RavelryPhoto(
    val id: Int?,
    val caption: String?,
    @SerialName("caption_html") val captionHtml: String?,
    @SerialName("copyright_holder") val copyrightHolder: String?,
    @SerialName("medium2_url") val medium2Url: String?,
    @SerialName("medium_url") val mediumUrl: String?,
    @SerialName("small2_url") val small2Url: String?,
    @SerialName("small_url") val smallUrl: String?,
    @SerialName("sort_order") val sortOrder: Int?,
    @SerialName("square_url") val squareUrl: String?,
    @SerialName("thumbnail_url") val thumbnailUrl: String?,
    @SerialName("user_id") val userId: Int?,
    @SerialName("x_offset") val xOffset: Int?,
    @SerialName("y_offset") val yOffset: Int?
)