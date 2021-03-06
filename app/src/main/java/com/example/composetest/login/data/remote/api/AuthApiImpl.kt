package com.example.composetest.login.data.remote.api

import android.util.Log
import com.example.composetest.login.data.remote.model.auth.*
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
        userAuthentication: UserAuthenticationRequest?
    ): UserAuthenticationResponse {
        return try {
            _httpClient.post {
                url("$_basePath/login")
                contentType(ContentType.Application.Json)
                body = userAuthentication!!
            }
        } catch (pipeline: ReceivePipelineException) {
            throw pipeline.cause
        }
    }


    override suspend fun userCheckRequest(
        contentType: String?,
        userCheck: UserCheckRequest?
    ): UserCheckResponse {
        return try {
            Log.d("checkUser", "Api, start. body: ${userCheck?.phoneNum}, path: $_basePath/check-user")
            _httpClient.post {
                url("$_basePath/check-user")
                contentType(ContentType.Application.Json)
                this.body = userCheck!!
            }
        } catch (pipeline: ReceivePipelineException) {
            Log.d("checkUser", "Api, error: ${userCheck?.phoneNum}")
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
        sendOTP: SendOTPRequest?
    ): SendOTPResponseDTO {

        return try {
            _httpClient.post() {
                url("$_basePath/send_otp")
                contentType(ContentType.Application.Json)
                body= sendOTP!!
            }
        } catch (pipeline: ReceivePipelineException) {
            throw pipeline.cause
        }
    }

    @Suppress("UNCHECKED_CAST")
    override suspend fun checkOTPRequest(
        contentType: String?,
        checkOTP: CheckOTPRequest?
    ): CheckOTPResponseDTO {

        return try {
            _httpClient.post {
                url("$_basePath/check_otp")
                contentType(ContentType.Application.Json)
                body= checkOTP!!
            }
        } catch (pipeline: ReceivePipelineException) {
            Log.d(
                "checkOTP",
                "AuthRepositoryImpl  Throwable ${pipeline.message}"
            )
            throw pipeline.cause
        }
    }

    override suspend fun userRegistrationRequest(
        contentType: String?,
        authorization: String?,
        userRegistration: UserRegistrationRequest?
    ): UserRegistrationResponse {
        return try {
            _httpClient.post {
                url("$_basePath/createUser")
                contentType(ContentType.Application.Json)
                body= userRegistration!!
            }
        } catch (pipeline: ReceivePipelineException) {
            throw pipeline.cause
        }
    }

    override suspend fun passwordResetRequest(
        contentType: kotlin.String?,
        passwordReset: PasswordResetRequest?,
        authorization: String?
    ): PasswordResetResponse {
        return try {
            _httpClient.post {
                url("$_basePath/change-password")
                contentType(ContentType.Application.Json)
                body= passwordReset!!
            }
        } catch (pipeline: ReceivePipelineException) {
            throw pipeline.cause
        }
    }

}
