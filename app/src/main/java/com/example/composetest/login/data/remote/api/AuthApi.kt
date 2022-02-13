package com.example.composetest.login.data.remote.api

import com.example.composetest.login.data.remote.model.auth.*

interface AuthApi {


    suspend fun getUserRequest(
        userId: String,
        contentType: String? = null,
        authorization: String? = null
    ): GetUserResponse


    suspend fun sendOTPRequest(
        contentType: String? = null,
        body: SendOTPRequest? = null
    ): SendOTPResponseDTO

    suspend fun checkOTPRequest(
        contentType: String? = null,
        body: CheckOTPRequest? = null
    ): CheckOTPResponseDTO

    suspend fun login(
        contentType: String? = null,
        bodyImpl: UserAuthenticationRequest? = null
    ): UserAuthenticationResponse?

    suspend fun userRegistrationRequest(
        contentType: String? = null,
        authorization: String? = null,
        body: UserRegistrationRequest? = null
    ): UserRegistrationResponse

    suspend fun userCheckRequest(contentType: String? = null, body: UserCheckRequest? = null) : UserCheckResponse

    suspend fun passwordResetRequest(
        contentType: kotlin.String? = null,
        body: PasswordResetRequest? = null,
        authorization: String?,
    ) : PasswordResetResponse
}

