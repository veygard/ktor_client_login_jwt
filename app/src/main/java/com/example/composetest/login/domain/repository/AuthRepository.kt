package com.example.composetest.login.domain.repository

import com.example.composetest.login.domain.model.Response
import com.example.composetest.login.domain.model.Token
import com.example.composetest.login.domain.model.User
import com.example.composetest.login.domain.model.auth.ChangePasswordResponse
import com.example.composetest.login.domain.model.auth.CheckOTPResponse
import com.example.composetest.login.domain.model.auth.CreateUserResponse
import com.example.composetest.login.domain.model.auth.SendOTPResponse

interface AuthRepository {

    suspend fun login(phone: String, password: String): Response<Token>
    suspend fun checkUser(phone: String): Response<Boolean>
    suspend fun getUser(jwt:String?): Response<User>

    suspend fun sendOtp(phoneNum: String):Response<SendOTPResponse>
    suspend fun checkOtp(phoneNum: String, otp:String): Response<CheckOTPResponse>

    suspend fun createUser(phoneNum: String, password:String): Response<CreateUserResponse>
    suspend fun changePassword(jwt:String?, password:String): Response<ChangePasswordResponse>

}