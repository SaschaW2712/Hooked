package com.saschaw.hooked.core.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RavelryPhoto(
    val id: Int? = null,
    val caption: String? = null,
    @SerialName("caption_html") val captionHtml: String? = null,
    @SerialName("copyright_holder") val copyrightHolder: String? = null,
    @SerialName("medium2_url") val medium2Url: String? = null,
    @SerialName("medium_url") val mediumUrl: String? = null,
    @SerialName("small2_url") val small2Url: String? = null,
    @SerialName("small_url") val smallUrl: String? = null,
    @SerialName("sort_order") val sortOrder: Int? = null,
    @SerialName("square_url") val squareUrl: String? = null,
    @SerialName("thumbnail_url") val thumbnailUrl: String? = null,
    @SerialName("user_id") val userId: Int? = null,
    @SerialName("x_offset") val xOffset: Int? = null,
    @SerialName("y_offset") val yOffset: Int? = null
)