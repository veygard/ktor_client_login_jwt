package com.example.composetest.login.data.remote.api

import android.util.Log
import com.example.composetest.login.data.remote.model.auth.UserAuthenticationResponse
import com.example.composetest.login.data.remote.model.auth.*
import com.example.composetest.login.data.remote.model.auth.PasswordResetResponse
import com.example.composetest.login.util.Constants
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.http.content.*
import kotlinx.serialization.json.Json


class AuthApiImpl(httpClient: HttpClient, json: Json) : AuthApi {
    private val _basePath = Constants.BASE_URL
    private val _httpClient = httpClient
    private val _json = json


    override suspend fun login(
        contentType: String?,
        bodyImpl: UserAuthenticationRequest?
    ): UserAuthenticationResponse {
        return try {
            _httpClient.post {
                url("$_basePath/login")
                contentType(ContentType.Application.Json)
                body = bodyImpl!!
            }
        } catch (pipeline: ReceivePipelineException) {
            throw pipeline.cause
        }
    }


    override suspend fun userCheckRequest(
        contentType: String?,
        body: UserCheckRequest?
    ): UserCheckResponse {
        return try {
            Log.d("checkUser", "Api, start. body: ${body?.phoneNum}, path: $_basePath/check-user")
            _httpClient.post {
                url("$_basePath/check-user")
                contentType(ContentType.Application.Json)
                this.body = body!!
            }
        } catch (pipeline: ReceivePipelineException) {
            Log.d("checkUser", "Api, error: ${body?.phoneNum}")
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

        return try {
            _httpClient.get {
                url("$_basePath/get-user/$userId")
                contentType(ContentType.Application.Json)
                headers{
                    append("Authorization", "Bearer $authorization")
                }
            }
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
