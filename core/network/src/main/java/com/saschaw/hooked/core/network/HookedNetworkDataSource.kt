package com.saschaw.hooked.core.network

import com.saschaw.hooked.core.model.FavoritesListPaginated
import com.saschaw.hooked.core.model.RavelryUser
import com.saschaw.hooked.core.model.SearchResultsPaginated

interface HookedNetworkDataSource {
     suspend fun fetchFavoritesList(): FavoritesListPaginated?
     suspend fun search(query: String): SearchResultsPaginated?
     suspend fun fetchCurrentUsername(): String?
}