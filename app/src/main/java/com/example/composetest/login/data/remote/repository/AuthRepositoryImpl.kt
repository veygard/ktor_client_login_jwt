package com.example.composetest.login.data.remote.repository

import android.util.Log
import com.example.composetest.login.data.remote.api.AuthApi
import com.example.composetest.login.data.remote.model.auth.*
import com.example.composetest.login.domain.model.Response
import com.example.composetest.login.domain.repository.AuthRepository
import com.example.composetest.login.data.remote.storage.TokenDTO
import com.example.composetest.login.domain.model.Token
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

internal class AuthRepositoryImpl (
    private val authApi: AuthApi,
    private val coroutineDispatcher: CoroutineDispatcher,
) : AuthRepository {


    override suspend fun login(
        phone: String,
        password: String
    ): Response<Token> = withContext(coroutineDispatcher) {
        try {
            Log.d("LoginViewModel", "AuthRepositoryImpl auth login started")
            val response = authApi.userAuthenticationRequest(
                bodyImpl = UserAuthenticationRequest(
                    mobile = phone,
                    password = password
                )
            )
            val tokenDTO = TokenDTO(
                jwt = response!!.jwt,
            )
            Response.Success(tokenDTO.toDomain())
        } catch (e: Throwable) {
            Log.d("LoginViewModel", "AuthRepositoryImpl auth exception message ${e.message}")
            Response.Error("exception message: ${e.message}" ?: "exception message null")
        }
    }

}