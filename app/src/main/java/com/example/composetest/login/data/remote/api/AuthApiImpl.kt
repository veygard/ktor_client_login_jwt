package com.example.composetest.login.data.remote.api

import com.example.composetest.login.data.remote.model.auth.UserAuthenticationResponse
import com.example.composetest.login.data.remote.model.auth.*
import com.example.composetest.login.data.remote.model.auth.PasswordResetResponse
import com.example.composetest.login.domain.model.TokenDTO
import com.example.composetest.login.util.Constants
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.android.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.http.*
import io.ktor.http.content.*
import kotlinx.serialization.json.Json


class AuthApiImpl (httpClient: HttpClient, json: Json) : AuthApi {
    private val _basePath = Constants.BASE_URL
    private val _httpClient = httpClient
    private val _json = json


    override suspend fun login(
        contentType: kotlin.String?,
        body: UserAuthenticationRequest?
    ): UserAuthenticationResponse {
        val builder = HttpRequestBuilder()

        builder.method = HttpMethod.Post
        builder.url {
            takeFrom(_basePath)
            encodedPath = encodedPath.let { startingPath ->
                path("/login")
                return@let startingPath + encodedPath.substring(1)
            }
        }
        @Suppress("SENSELESS_COMPARISON")
        if (body != null) {
            builder.body = TextContent(

                _json.encodeToString(
                    UserAuthenticationRequest.serializer(),
                    body
                ),
                ContentType.Application.Json.withoutParameters()
            )
        }

        try {
            val serializer = UserAuthenticationResponse.serializer()

            //not primitive type
            val result: String = _httpClient.request(builder)
            return _json.decodeFromString(serializer, result)
        } catch (pipeline: ReceivePipelineException) {
            throw pipeline.cause
        }
    }

    @Suppress("UNCHECKED_CAST")
    override suspend fun authorizeOTPRequest(
        contentType: String?,
        body: AuthorizeOTPRequest?
    ): AuthorizeOTPResponse {
        val builder = HttpRequestBuilder()


        builder.method = HttpMethod.Post
        builder.url {
            takeFrom(_basePath)
            encodedPath = encodedPath.let { startingPath ->
                path("/login")
                return@let startingPath + encodedPath.substring(1)
            }
        }
        @Suppress("SENSELESS_COMPARISON")
        if (body != null) {
            builder.body = TextContent(

                _json.encodeToString(
                    AuthorizeOTPRequest.serializer(),
                    body
                ),
                ContentType.Application.Json.withoutParameters()
            )
        }

        try {
            val serializer = AuthorizeOTPResponse.serializer()

            //not primitive type
            val result: String = _httpClient.request(builder)
            return _json.decodeFromString(serializer, result)
        } catch (pipeline: ReceivePipelineException) {
            throw pipeline.cause
        }
    }

    @Suppress("UNCHECKED_CAST")
    override suspend fun getUserRequest(
        userId: String,
        contentType: String?,
        authorization: String?
    ): GetUserResponse {
        val builder = HttpRequestBuilder()

        builder.method = HttpMethod.Get
        builder.url {
            takeFrom(_basePath)
            encodedPath = encodedPath.let { startingPath ->
                path("get-user/$userId")
                return@let startingPath + encodedPath.substring(1)
            }
        }

        with(builder.headers) {
            append("Accept", "application/json")
            append("Authorization", "$authorization")
        }

        try {
            val serializer = GetUserResponse.serializer()

            //not primitive type
            val result: String = _httpClient.request(builder)
            return _json.decodeFromString(serializer, result)
        } catch (pipeline: ReceivePipelineException) {
            throw pipeline.cause
        }
    }


    @Suppress("UNCHECKED_CAST")
    override suspend fun jWTRefreshRequest(
        contentType: String?,
        body: JWTRefreshRequest?
    ): JWTRefreshResponse {
        val builder = HttpRequestBuilder()

        builder.method = HttpMethod.Post
        builder.url {
            takeFrom(_basePath)
            encodedPath = encodedPath.let { startingPath ->
                path("auth-api/v1/jwt-refresh")
                return@let startingPath + encodedPath.substring(1)
            }
        }
        @Suppress("SENSELESS_COMPARISON")
        if (body != null) {
            builder.body = TextContent(

                _json.encodeToString(
                    JWTRefreshRequest.serializer(),

                    body
                ),
                ContentType.Application.Json.withoutParameters()
            )
        }

        with(builder.headers) {
            append("Accept", "application/json")
        }

        try {
            val serializer = JWTRefreshResponse.serializer()

            //not primitive type
            val result: String = _httpClient.request(builder)
            return _json.decodeFromString(serializer, result)
        } catch (pipeline: ReceivePipelineException) {
            throw pipeline.cause
        }
    }


    @Suppress("UNCHECKED_CAST")
    override suspend fun sendOTPRequest(
        contentType: String?,
        body: SendOTPRequest?
    ): SendOTPResponse {
        val builder = HttpRequestBuilder()

        builder.method = HttpMethod.Post
        builder.url {
            takeFrom(_basePath)
            encodedPath = encodedPath.let { startingPath ->
                path("auth-api/v1/send-otp")
                return@let startingPath + encodedPath.substring(1)
            }
        }
        @Suppress("SENSELESS_COMPARISON")
        if (body != null) {
            builder.body = TextContent(

                _json.encodeToString(
                    SendOTPRequest.serializer(),
                    body
                ),
                ContentType.Application.Json.withoutParameters()
            )
        }

        with(builder.headers) {
            append("Accept", "application/json")
        }

        try {
            val serializer = SendOTPResponse.serializer()

            //not primitive type
            val result: String = _httpClient.request(builder)
            return _json.decodeFromString(serializer, result)
        } catch (pipeline: ReceivePipelineException) {
            throw pipeline.cause
        }
    }


    @Suppress("UNCHECKED_CAST")
    override suspend fun userRegistrationRequest(
        contentType: String?,
        authorization: String?,
        body: UserRegistrationRequest?
    ): UserRegistrationResponse {
        val builder = HttpRequestBuilder()

        builder.method = HttpMethod.Post
        builder.url {
            takeFrom(_basePath)
            encodedPath = encodedPath.let { startingPath ->
                path("auth-api/v1/register")
                return@let startingPath + encodedPath.substring(1)
            }
        }
        @Suppress("SENSELESS_COMPARISON")
        if (body != null) {
            builder.body = TextContent(

                _json.encodeToString(
                    UserRegistrationRequest.serializer(),

                    body
                ),
                ContentType.Application.Json.withoutParameters()
            )
        }

        with(builder.headers) {
            append("Accept", "application/json")
            append("Authorization", "$authorization")
        }

        try {
            val serializer = UserRegistrationResponse.serializer()

            //not primitive type
            val result: String = _httpClient.request(builder)
            return _json.decodeFromString(serializer, result)
        } catch (pipeline: ReceivePipelineException) {
            throw pipeline.cause
        }
    }

    @Suppress("UNCHECKED_CAST")
    override suspend fun userCheckRequest(
        contentType: kotlin.String?,
        body: UserCheckRequest?
    ): UserCheckResponse {
        val builder = HttpRequestBuilder()

        builder.method = HttpMethod.Post
        builder.url {
            takeFrom(_basePath)
            encodedPath = encodedPath.let { startingPath ->
                path("auth-api/v1/user-check")
                return@let startingPath + encodedPath.substring(1)
            }
        }
        @Suppress("SENSELESS_COMPARISON")
        if (body != null) {
            builder.body = TextContent(

                _json.encodeToString(
                    UserCheckRequest.serializer(),

                    body
                ),
                ContentType.Application.Json.withoutParameters()
            )
        }

        with(builder.headers) {
            append("Accept", "application/json")
        }

        try {
            val serializer = UserCheckResponse.serializer()

            //not primitive type
            val result: String = _httpClient.request(builder)
            return _json.decodeFromString(serializer, result)
        } catch (pipeline: ReceivePipelineException) {
            throw pipeline.cause
        }
    }

    @Suppress("UNCHECKED_CAST")
    override suspend fun passwordResetRequest(
        contentType: kotlin.String?,
        body: PasswordResetRequest?,
        authorization: String?
    ): PasswordResetResponse {
        val builder = HttpRequestBuilder()

        builder.method = HttpMethod.Post
        builder.url {
            takeFrom(_basePath)
            encodedPath = encodedPath.let { startingPath ->
                path("auth-api/v1/password-reset")
                return@let startingPath + encodedPath.substring(1)
            }
        }
        @Suppress("SENSELESS_COMPARISON")
        if (body != null) {
            builder.body = TextContent(

                _json.encodeToString(
                    PasswordResetRequest.serializer(),

                    body
                ),
                ContentType.Application.Json.withoutParameters()
            )
        }

        with(builder.headers) {
            append("Accept", "application/json")
            append("Authorization", "$authorization")
        }

        try {
            val serializer = PasswordResetResponse.serializer()

            //not primitive type
            val result: String = _httpClient.request(builder)
            return _json.decodeFromString(serializer, result)
        } catch (pipeline: ReceivePipelineException) {
            throw pipeline.cause
        }
    }

}
