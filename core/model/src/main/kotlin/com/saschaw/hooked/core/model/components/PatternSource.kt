package com.saschaw.hooked.core.model.components

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PatternSource(
    @SerialName("amazon_rating") val amazonRating: Float? = null,
    @SerialName("amazon_url") val amazonUrl: String? = null,
    val author: String? = null,
    val id: Int,
    @SerialName("list_price") val listPrice: Float? = null,
    val name: String,
    @SerialName("out_of_print") val outOfPrint: Boolean? = null,
    @SerialName("pattern_source_type_id") val patternSourceTypeId: Int? = null,
    @SerialName("pattern_source_type") val patternSourceType: String? = null,
    @SerialName("patterns_count") val patternsCount: Int? = null,
    val permalink: String,
    val price: Float? = null,
    @SerialName("shelf_image_path") val shelfImagePath: String? = null,
    val url: String? = null
)