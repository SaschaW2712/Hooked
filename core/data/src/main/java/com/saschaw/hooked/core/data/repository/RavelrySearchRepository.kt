package com.saschaw.hooked.core.data.repository

import com.saschaw.hooked.core.model.lists.search.SearchResultsPaginated
import com.saschaw.hooked.core.network.HookedNetworkDataSource
import javax.inject.Inject

interface RavelrySearchRepository {
    suspend fun search(query: String): SearchResultsPaginated?
    suspend fun getHotRightNow(): SearchResultsPaginated?
}

internal class RavelrySearchRepositoryImpl @Inject constructor(
    private val network: HookedNetworkDataSource
): RavelrySearchRepository {
    override suspend fun search(query: String): SearchResultsPaginated? {
        return network.search(query)
    }

    override suspend fun getHotRightNow(): SearchResultsPaginated? {
        // Searching with an empty query returns all patterns, ordered by current popularity
        return network.search("")
    }
}