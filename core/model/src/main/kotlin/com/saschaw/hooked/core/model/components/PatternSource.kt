package com.saschaw.hooked.core.model.components

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PatternSource(
    @SerialName("amazon_rating") val amazonRating: Float? = null,
    @SerialName("amazon_url") val amazonUrl: String? = null,
    val author: String? = null,
    val id: Int,
    @SerialName("list_price") val listPrice: String? = null,
    val name: String,
    @SerialName("out_of_print") val outOfPrint: Boolean? = null,
    @SerialName("pattern_source_type_id") val patternSourceTypeId: Int? = null,
    @SerialName("pattern_source_type") val patternSourceType: PatternSourceType? = null,
    @SerialName("patterns_count") val patternsCount: Int? = null,
    val permalink: String,
    val price: String? = null,
    @SerialName("shelf_image_path") val shelfImagePath: String? = null,
    val url: String? = null
)
@Serializable
data class PatternSourceType(
    @SerialName("can_add_to_library") val canAddToLibrary: Boolean?,
    val id: Int,
    @SerialName("long_name") val longName: String,
    val name: String,
    @SerialName("requires_url") val requiresUrl: Boolean?
)