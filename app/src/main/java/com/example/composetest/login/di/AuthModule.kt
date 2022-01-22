package com.example.composetest.login.di

import com.example.composetest.login.data.remote.api.AuthApi
import com.example.composetest.login.data.remote.api.AuthApiImpl
import com.example.composetest.login.data.remote.repository.AuthRepositoryImpl
import com.example.composetest.login.domain.repository.AuthRepository
import com.example.composetest.login.domain.use_cases.auth.LoginUseCase
import com.example.composetest.login.domain.use_cases.auth.AuthUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthModule {

    @Provides
    @Singleton
    fun provideAuthApi(): AuthApi = AuthApiImpl(NetworkModule.provideHttpClient(), NetworkModule.provideJson())

    @Provides
    @Singleton
    fun provideAuthRepository(): AuthRepository = AuthRepositoryImpl(provideAuthApi(), NetworkModule.provideCoroutineDispatcher())

    @Provides
    @Singleton
    fun provideUseCases(): AuthUseCases {
        return AuthUseCases(
            loginUseCase = LoginUseCase(authRepository = provideAuthRepository())
        )
    }

}