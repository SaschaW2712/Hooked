package com.saschaw.hooked.core.data.repository

import com.saschaw.hooked.core.network.HookedNetworkDataSource
import javax.inject.Inject

interface RavelryUserDataRepository {
    suspend fun fetchCurrentUsername(): String?
}

internal class RavelryUserDataRepositoryImpl @Inject constructor(
    private val network: HookedNetworkDataSource
): RavelryUserDataRepository {
    override suspend fun fetchCurrentUsername(): String? {
        return network.fetchCurrentUsername()
    }
}