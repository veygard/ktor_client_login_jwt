package com.example.composetest.login.di

import android.content.Context
import com.example.composetest.login.data.local.model.DataStoreOperations
import com.example.composetest.login.data.remote.api.AuthApi
import com.example.composetest.login.data.remote.api.AuthApiImpl
import com.example.composetest.login.data.remote.repository.AuthRepositoryImpl
import com.example.composetest.login.di.DataOperations.provideDataStoreOperations
import com.example.composetest.login.di.NetworkModule.provideHttpClient
import com.example.composetest.login.domain.repository.AuthRepository
import com.example.composetest.login.domain.use_cases.auth.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.ktor.client.*
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.serialization.json.Json
import javax.inject.Singleton

@DelicateCoroutinesApi
@Module
@InstallIn(SingletonComponent::class)
object AuthModule {

    @Provides
    @Singleton
    fun provideAuthApi(
        httpClient: HttpClient,
        json: Json
    ): AuthApi = AuthApiImpl(httpClient, json)

    @Provides
    @Singleton
    fun provideAuthRepository(
        authApi: AuthApi,
        coroutineDispatcher: CoroutineDispatcher,
        dataStoreOperations: DataStoreOperations
    ): AuthRepository = AuthRepositoryImpl(authApi, coroutineDispatcher, dataStoreOperations)

    @Provides
    @Singleton
    fun provideUseCases(
        authRepository: AuthRepository
    ): AuthUseCases {
        return AuthUseCases(
            loginUseCase = LoginUseCase(authRepository = authRepository),
            getUser =  GetUserUseCase(authRepository = authRepository),
            checkUserUseCase = CheckUserUseCase(authRepository = authRepository),
            sendOtpUseCase = SendOtpUseCase(authRepository = authRepository),
            checkOtpUseCase = CheckOtpUseCase(authRepository = authRepository)
        )
    }

}