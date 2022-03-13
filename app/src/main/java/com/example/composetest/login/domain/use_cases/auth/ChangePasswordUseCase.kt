package com.example.composetest.login.domain.use_cases.auth

import com.example.composetest.login.domain.repository.AuthRepository

class ChangePasswordUseCase(
    private val authRepository: AuthRepository
) {
    suspend fun start(password: String, phoneNum: String, jwt: String) =
        authRepository.changePassword(jwt = jwt, password = password, phoneNum = phoneNum)
}