package com.saschaw.hooked.core.datastore.di


import com.saschaw.hooked.core.datastore.HookedPreferencesDataSource
import com.saschaw.hooked.core.datastore.PreferencesDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataStoreModule {
    @Binds
    internal abstract fun bindsDataStore(
        dataStore: HookedPreferencesDataSource
    ): PreferencesDataSource
}
