package com.saschaw.hooked.core.data.di

import com.saschaw.hooked.core.data.repository.RavelryPatternDetailsRepository
import com.saschaw.hooked.core.data.repository.RavelryPatternDetailsRepositoryImpl
import com.saschaw.hooked.core.data.repository.RavelrySearchRepository
import com.saschaw.hooked.core.data.repository.RavelrySearchRepositoryImpl
import com.saschaw.hooked.core.data.repository.RavelryUserDataRepository
import com.saschaw.hooked.core.data.repository.RavelryUserDataRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {
    @Binds
    internal abstract fun bindsRavelryUserDataRepository(
        userDataRepository: RavelryUserDataRepositoryImpl,
    ): RavelryUserDataRepository

    @Binds
    internal abstract fun bindsRavelrySearchRepository(
        searchRepository: RavelrySearchRepositoryImpl,
    ): RavelrySearchRepository

    @Binds
    internal abstract fun bindsRavelryPatternDetailsRepository(
        searchRepository: RavelryPatternDetailsRepositoryImpl,
    ): RavelryPatternDetailsRepository
}
