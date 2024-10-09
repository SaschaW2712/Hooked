package com.saschaw.hooked.core.network.di

import com.saschaw.hooked.core.network.HookedNetworkDataSource
import com.saschaw.hooked.core.network.RetrofitHookedNetwork
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class NetworkModule {
    @Binds
    internal abstract fun bindsHookedNetworkDataSource(
        dataSource: RetrofitHookedNetwork,
    ): HookedNetworkDataSource
}
