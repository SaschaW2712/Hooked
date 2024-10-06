package com.saschaw.hooked.core.authentication.di

import android.content.Context
import com.saschaw.hooked.core.authentication.AuthenticationManager
import com.saschaw.hooked.core.authentication.RavelryAuthenticationManager
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import net.openid.appauth.AuthorizationService
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AuthenticationModule {
    companion object {
        @Provides
        @Singleton
        fun provideAuthorizationService(
            @ApplicationContext context: Context
        ): AuthorizationService {
            return AuthorizationService(context)
        }
    }

    @Binds
    internal abstract fun bindAuthenticationManager(
        authenticationManager: RavelryAuthenticationManager
    ): AuthenticationManager
}
