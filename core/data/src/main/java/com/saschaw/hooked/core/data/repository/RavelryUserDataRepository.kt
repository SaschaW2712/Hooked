package com.saschaw.hooked.core.data.repository

import com.saschaw.hooked.core.model.FavoritesListPaginated
import com.saschaw.hooked.core.model.User
import com.saschaw.hooked.core.network.HookedNetworkDataSource
import javax.inject.Inject

interface RavelryUserDataRepository {
    suspend fun getFavoritesList(): FavoritesListPaginated
    suspend fun getCurrentUser(): User
}

internal class RavelryUserDataRepositoryImpl @Inject constructor(
    private val network: HookedNetworkDataSource
): RavelryUserDataRepository {
    override suspend fun getFavoritesList(): FavoritesListPaginated {
        return network.getFavoritesList()
    }

    override suspend fun getCurrentUser(): User {
        return network.getCurrentUser()
    }
}