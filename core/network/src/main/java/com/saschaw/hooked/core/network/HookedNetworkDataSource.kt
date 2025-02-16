package com.saschaw.hooked.core.network

import com.saschaw.hooked.core.model.lists.favorites.FavoritesListPaginated
import com.saschaw.hooked.core.model.lists.search.SearchResultsPaginated

interface HookedNetworkDataSource {
     suspend fun fetchFavoritesList(): FavoritesListPaginated?
     suspend fun search(query: String): SearchResultsPaginated?
     suspend fun fetchCurrentUsername(): String?
}