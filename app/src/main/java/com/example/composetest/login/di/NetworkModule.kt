package com.example.composetest.login.di

import com.example.composetest.login.data.remote.api.AuthApi
import com.example.composetest.login.data.remote.api.AuthApiImpl
import com.example.composetest.login.data.remote.repository.AuthRepositoryImpl
import com.example.composetest.login.domain.repository.AuthRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.features.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.serialization.json.Json
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideJson():Json{
        return Json { ignoreUnknownKeys = true }
    }

    @Provides
    fun provideCoroutineDispatcher():CoroutineDispatcher = Dispatchers.Main


    @Provides
    @Singleton
    fun provideHttpClient(): HttpClient {
        return HttpClient(Android) {
            install(JsonFeature) {
                serializer = KotlinxSerializer(provideJson())
            }

            HttpResponseValidator {
                validateResponse { response ->
                    val statusCode = response.status.value
                    when (statusCode) {
                        in 300..399 -> throw RedirectResponseException(response, "")
                        in 400..499 -> throw ClientRequestException(response, "")
                        in 500..599 -> throw ServerResponseException(response, "")
                    }
                    if (statusCode >= 600) {
                        throw ResponseException(response, "")
                    }
                }
            }
        }
    }

}