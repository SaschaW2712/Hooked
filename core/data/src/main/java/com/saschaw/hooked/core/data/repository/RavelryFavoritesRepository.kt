package com.saschaw.hooked.core.data.repository

import com.saschaw.hooked.core.model.Bookmark
import com.saschaw.hooked.core.model.lists.favorites.FavoritesListPaginated
import com.saschaw.hooked.core.network.HookedNetworkDataSource
import javax.inject.Inject

interface RavelryFavoritesRepository {
    suspend fun fetchFavoritesList(): FavoritesListPaginated?
    suspend fun addToFavorites(id: Int): Bookmark?
    suspend fun removeFavorite(id: Int): Bookmark?
}

internal class RavelryFavoritesRepositoryImpl @Inject constructor(
    private val network: HookedNetworkDataSource
): RavelryFavoritesRepository {
    override suspend fun fetchFavoritesList(): FavoritesListPaginated? {
        return network.fetchFavoritesList()
    }

    override suspend fun addToFavorites(id: Int): Bookmark? {
        return network.savePatternToFavorites(id)
    }

    override suspend fun removeFavorite(id: Int): Bookmark? {
        return network.removePatternFromFavorites(id)
    }
}