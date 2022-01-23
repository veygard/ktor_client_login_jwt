package com.example.composetest.login.data.remote.repository

import android.content.Context
import android.util.Log
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.viewModelScope
import com.example.composetest.login.data.local.model.DataStoreOperations
import com.example.composetest.login.data.remote.api.AuthApi
import com.example.composetest.login.data.remote.model.auth.*
import com.example.composetest.login.domain.repository.AuthRepository
import com.example.composetest.login.data.remote.storage.TokenDTO
import com.example.composetest.login.domain.model.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.stateIn

@DelicateCoroutinesApi
internal class AuthRepositoryImpl(
    private val authApi: AuthApi,
    private val coroutineDispatcher: CoroutineDispatcher,
    private val dataStore: DataStoreOperations
) : AuthRepository {

    override suspend fun login(
        phone: String,
        password: String
    ): Response<Token> = withContext(coroutineDispatcher) {
        try {
            Log.d("LoginViewModel", "AuthRepositoryImpl auth login started")
            val response = authApi.login(
                bodyImpl = UserAuthenticationRequest(
                    mobile = phone,
                    password = password
                )
            )
            dataStore.saveToken(response?.jwt)
            val tokenDTO = TokenDTO(
                jwt = response!!.jwt,
            )
            Response.Success(tokenDTO.toDomain())
        } catch (e: Throwable) {
            Log.d("LoginViewModel", "AuthRepositoryImpl auth exception message ${e.message}")
            Response.Error("exception message: ${e.message}" ?: "exception message null")
        }
    }

    override suspend fun getUser(): Response<User> = withContext(coroutineDispatcher) {
        try {
            var jwt: String? = null
            GlobalScope.launch(coroutineDispatcher) {
                jwt = dataStore.readToken().stateIn(this).value
            }
            val userId = decodeUserId(jwt ?: "")
            val response = authApi.getUserRequest(userId = userId ?: "", authorization = jwt)

            Response.Success(response.toDomain())
        } catch (e: Throwable) {
            Response.Error("exception message: ${e.message}")
        }
    }

}