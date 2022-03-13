package com.example.composetest.login.domain.use_cases.auth

import com.example.composetest.login.domain.repository.AuthRepository

class CreateUserUseCase(
    private val authRepository: AuthRepository
) {
    suspend fun start(phoneNum: String, password:String) = authRepository.createUser(phoneNum,password)
}