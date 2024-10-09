package com.saschaw.hooked.core.data.di

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
        topicsRepository: RavelryUserDataRepositoryImpl,
    ): RavelryUserDataRepository
}
