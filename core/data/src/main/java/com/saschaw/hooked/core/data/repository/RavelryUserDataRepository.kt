package com.saschaw.hooked.core.data.repository

import com.saschaw.hooked.core.model.FavoritesListPaginated
import com.saschaw.hooked.core.network.HookedNetworkDataSource
import javax.inject.Inject

interface RavelryUserDataRepository {
    suspend fun fetchFavoritesList(): FavoritesListPaginated?
    suspend fun fetchCurrentUsername(): String?
}

internal class RavelryUserDataRepositoryImpl @Inject constructor(
    private val network: HookedNetworkDataSource
): RavelryUserDataRepository {
    override suspend fun fetchFavoritesList(): FavoritesListPaginated? {
        return network.fetchFavoritesList()
    }

    override suspend fun fetchCurrentUsername(): String? {
        return network.fetchCurrentUsername()
    }
}