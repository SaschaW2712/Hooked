package com.saschaw.hooked.core.data.repository

import com.saschaw.hooked.core.model.FavoritesListPaginated
import com.saschaw.hooked.core.model.RavelryUser
import com.saschaw.hooked.core.network.HookedNetworkDataSource
import javax.inject.Inject

interface RavelryUserDataRepository {
    suspend fun getFavoritesList(): FavoritesListPaginated
    suspend fun getCurrentUser(): RavelryUser
}

internal class RavelryUserDataRepositoryImpl @Inject constructor(
    private val network: HookedNetworkDataSource
): RavelryUserDataRepository {
    override suspend fun getFavoritesList(): FavoritesListPaginated {
        return network.getFavoritesList()
    }

    override suspend fun getCurrentUser(): RavelryUser {
        return network.getCurrentUser()
    }
}