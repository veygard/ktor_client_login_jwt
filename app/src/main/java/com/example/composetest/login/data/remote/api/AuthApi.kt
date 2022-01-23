package com.example.composetest.login.data.remote.api

import com.example.composetest.login.data.remote.model.auth.*
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.http.content.*

interface AuthApi {

    suspend fun authorizeOTPRequest(
        contentType: String? = null,
        body: AuthorizeOTPRequest? = null
    ): AuthorizeOTPResponse

    suspend fun getUserRequest(
        userId: String,
        contentType: String? = null,
        authorization: String? = null
    ): GetUserResponse

    suspend fun jWTRefreshRequest(
        contentType: String? = null,
        body: JWTRefreshRequest? = null
    ): JWTRefreshResponse

    suspend fun sendOTPRequest(
        contentType: String? = null,
        body: SendOTPRequest? = null
    ): SendOTPResponse

    suspend fun userAuthenticationRequest(
        contentType: String? = null,
        bodyImpl: UserAuthenticationRequest? = null
    ): UserAuthenticationResponse?

    suspend fun userRegistrationRequest(
        contentType: String? = null,
        authorization: String? = null,
        body: UserRegistrationRequest? = null
    ): UserRegistrationResponse

    suspend fun userCheckRequest(contentType: kotlin.String? = null, body: UserCheckRequest? = null) : UserCheckResponse

    suspend fun passwordResetRequest(
        contentType: kotlin.String? = null,
        body: PasswordResetRequest? = null,
        authorization: String?,
    ) : PasswordResetResponse
}

