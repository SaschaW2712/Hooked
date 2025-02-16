package com.saschaw.hooked.core.model

import com.saschaw.hooked.core.model.pattern.PatternListItem
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Bookmark(
    val bundles: List<Bundle>,
    val comment: String?,
    @SerialName("created_at") val createdAt: String,
    val favorited: PatternListItem,
    val id: Int,
    @SerialName("tag_list") val tagList: String?,
    val type: String,
)

@Serializable
data class CreateBookmark(
    @SerialName("favorited_id") val id: String,
    val type: String,
    val comment: String,
    @SerialName("tag_list") val tagList: String?,
)
