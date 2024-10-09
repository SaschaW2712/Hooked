package com.saschaw.hooked.core.data.repository

import com.saschaw.hooked.core.network.FavoritesListPaginated
import com.saschaw.hooked.core.network.HookedNetworkDataSource
import javax.inject.Inject

interface RavelryUserDataRepository {
    suspend fun getFavoritesList(): FavoritesListPaginated
}

internal class RavelryUserDataRepositoryImpl @Inject constructor(
    private val network: HookedNetworkDataSource
): RavelryUserDataRepository {
    override suspend fun getFavoritesList(): FavoritesListPaginated {
        return network.refreshFavoritesList()
    }

}