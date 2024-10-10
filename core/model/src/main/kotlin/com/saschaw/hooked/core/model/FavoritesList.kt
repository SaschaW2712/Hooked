package com.saschaw.hooked.core.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FavoritesListPaginated(
    val favorites: List<FavoritesListItem>,
    val paginator: Paginator
)

@Serializable
data class FavoritesListItem(
    val comment: String?,
    @SerialName("created_at") val createdAt: String,
    val id: Int,
    @SerialName("tag_list") val tagList: String,
    val type: String,
    val favorited: Pattern,
)