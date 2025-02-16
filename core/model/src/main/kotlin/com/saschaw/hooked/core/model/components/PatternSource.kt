package com.saschaw.hooked.core.model.components

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PatternSource(
    @SerialName("amazon_rating") val amazonRating: Float?,
    @SerialName("amazon_url") val amazonUrl: String?,
    val author: String?,
    val id: Int,
    @SerialName("list_price") val listPrice: String?,
    val name: String,
    @SerialName("out_of_print") val outOfPrint: Boolean?,
    @SerialName("pattern_source_type_id") val patternSourceTypeId: Int?,
    @SerialName("pattern_source_type") val patternSourceType: PatternSourceType? = null,
    @SerialName("patterns_count") val patternsCount: Int?,
    val permalink: String,
    val price: String?,
    @SerialName("shelf_image_path") val shelfImagePath: String?,
    val url: String?
)

@Serializable
data class PatternSourceType(
    @SerialName("can_add_to_library") val canAddToLibrary: Boolean?,
    val id: Int,
    @SerialName("long_name") val longName: String?,
    val name: String?,
    @SerialName("requires_url") val requiresUrl: Boolean?
)