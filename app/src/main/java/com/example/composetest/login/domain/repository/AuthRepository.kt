package com.example.composetest.login.domain.repository

import com.example.composetest.login.domain.model.AuthorizeUser
import com.example.composetest.login.domain.model.Response
import com.example.composetest.login.domain.model.Token
import com.example.composetest.login.domain.model.User

interface AuthRepository {


    suspend fun login(phone: String, password: String): Response<Token>
    suspend fun getUser(jwt:String?): Response<User>

}