package com.saschaw.hooked.core.network

interface HookedNetworkDataSource {
     suspend fun refreshFavoritesList(): FavoritesListPaginated
}