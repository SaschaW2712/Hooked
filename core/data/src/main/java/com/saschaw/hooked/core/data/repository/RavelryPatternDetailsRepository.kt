package com.saschaw.hooked.core.data.repository

import com.saschaw.hooked.core.model.pattern.PatternFull
import com.saschaw.hooked.core.network.HookedNetworkDataSource
import javax.inject.Inject

interface RavelryPatternDetailsRepository {
    suspend fun getPatternDetails(id: Int): PatternFull?
}

internal class RavelryPatternDetailsRepositoryImpl @Inject constructor(
    private val network: HookedNetworkDataSource
): RavelryPatternDetailsRepository {
    override suspend fun getPatternDetails(id: Int): PatternFull? {
        return network.fetchPatternDetails(id)?.pattern
    }
}