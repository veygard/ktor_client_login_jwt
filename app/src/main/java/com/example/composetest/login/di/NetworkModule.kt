package com.example.composetest.login.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.aakira.napier.Napier
import io.ktor.client.*
import io.ktor.client.features.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*
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
        return HttpClient() {
            install(JsonFeature) {
                serializer = KotlinxSerializer(provideJson())
            }

            install(Logging) {
                logger = object: Logger {
                    override fun log(message: String) {
                        logger("login_app", message)
                        println("login_app: $message")
                    }
                }
                level = LogLevel.ALL
            }
            install(HttpTimeout) {
                requestTimeoutMillis = 1000
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

fun logger(tag: String, message: String) {
    Napier.v(
        tag = tag,
        message = message
    )
}