package com.example.composetest.login.di

import android.content.Context
import com.example.composetest.login.data.remote.api.AuthApi
import com.example.composetest.login.data.remote.api.AuthApiImpl
import com.example.composetest.login.data.remote.repository.AuthRepositoryImpl
import com.example.composetest.login.di.DataOperations.provideDataStoreOperations
import com.example.composetest.login.di.NetworkModule.provideHttpClient
import com.example.composetest.login.domain.repository.AuthRepository
import com.example.composetest.login.domain.use_cases.auth.LoginUseCase
import com.example.composetest.login.domain.use_cases.auth.AuthUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthModule {

    @Provides
    @Singleton
    fun provideAuthApi(): AuthApi = AuthApiImpl(provideHttpClient(), NetworkModule.provideJson())

    @Provides
    @Singleton
    fun provideAuthRepository(
        @ApplicationContext context: Context
    ): AuthRepository = AuthRepositoryImpl(provideAuthApi(), NetworkModule.provideCoroutineDispatcher(), provideDataStoreOperations(context))

    @Provides
    @Singleton
    fun provideUseCases(
        @ApplicationContext context1: Context
    ): AuthUseCases {
        return AuthUseCases(
            loginUseCase = LoginUseCase(authRepository = provideAuthRepository(context1))
        )
    }

}