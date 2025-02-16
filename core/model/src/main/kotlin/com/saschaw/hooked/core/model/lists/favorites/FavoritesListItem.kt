package com.saschaw.hooked.core.model.lists.favorites

import com.saschaw.hooked.core.model.pattern.PatternListItem
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FavoritesListItem(
    val comment: String?,
    @SerialName("created_at") val createdAt: String,
    val id: Int,
    @SerialName("tag_list") val tagList: String,
    val type: String,
    val favorited: PatternListItem,
)