package com.example.composetest.login.domain.repository

import com.example.composetest.login.domain.model.Response
import com.example.composetest.login.domain.model.Token
import com.example.composetest.login.domain.model.User
import com.example.composetest.login.navigation.AuthFlowEnum

interface AuthRepository {


    suspend fun login(phone: String, password: String): Response<Token>
    suspend fun checkUser(phone: String): Response<Boolean>
    suspend fun getUser(jwt:String?): Response<User>

}