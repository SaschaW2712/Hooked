package com.saschaw.hooked.core.network

import com.saschaw.hooked.core.model.lists.favorites.FavoritesListPaginated
import com.saschaw.hooked.core.model.lists.search.SearchResultsPaginated
import com.saschaw.hooked.core.model.Bookmark

interface HookedNetworkDataSource {
     suspend fun fetchFavoritesList(): FavoritesListPaginated?
     suspend fun search(query: String): SearchResultsPaginated?
     suspend fun fetchPatternDetails(id: Int): PatternDetailsResponse?
     suspend fun fetchCurrentUsername(): String?
     suspend fun savePatternToFavorites(id: String): Bookmark?
}