package com.example.composetest.login.data.remote.repository

import android.util.Log
import com.example.composetest.login.data.local.model.DataStoreOperations
import com.example.composetest.login.data.remote.api.AuthApi
import com.example.composetest.login.data.remote.model.auth.*
import com.example.composetest.login.domain.repository.AuthRepository
import com.example.composetest.login.data.remote.storage.TokenDTO
import com.example.composetest.login.domain.model.*
import com.example.composetest.login.navigation.AuthFlowEnum
import kotlinx.coroutines.*

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
            Response.Error("exception message: ${e.message}")
        }
    }

    override suspend fun checkUser(phone: String): Response<Boolean>  = withContext(coroutineDispatcher) {
        try {
            val response = authApi.userCheckRequest(body = UserCheckRequest(phoneNum = phone))
            Log.d("checkUser", "AuthRepositoryImpl, response.isFound: ${response.isFound ?: "null"}")
            Response.Success(response.isFound!!)
        }catch (e: Throwable) {
            Response.Error("exception message: ${e.message}")
        }
    }

    override suspend fun getUser(jwt: String?): Response<User> = withContext(coroutineDispatcher) {
        try {
            val userId = decodeUserId(jwt ?: "")
            delay(500) //чтобы прогресс бар покрутился)
            val response = authApi.getUserRequest(userId = userId ?: "", authorization = jwt)
            Response.Success(response.toDomain())
        } catch (e: Throwable) {
            Response.Error("exception message: ${e.message}")
        }
    }

}