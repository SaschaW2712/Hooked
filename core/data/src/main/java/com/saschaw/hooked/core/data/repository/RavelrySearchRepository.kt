package com.saschaw.hooked.core.data.repository

import com.saschaw.hooked.core.model.SearchResultsPaginated
import com.saschaw.hooked.core.network.HookedNetworkDataSource
import javax.inject.Inject

interface RavelrySearchRepository {
    suspend fun search(query: String): SearchResultsPaginated?
}

internal class RavelrySearchRepositoryImpl @Inject constructor(
    private val network: HookedNetworkDataSource
): RavelrySearchRepository {
    override suspend fun search(query: String): SearchResultsPaginated? {
        return network.search(query)
    }
}