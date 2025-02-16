package com.saschaw.hooked.core.model.lists.favorites

import com.saschaw.hooked.core.model.lists.Paginator
import kotlinx.serialization.Serializable

@Serializable
data class FavoritesListPaginated(
    val favorites: List<FavoritesListItem>,
    val paginator: Paginator
)

