package com.example.composetest.login.domain.use_cases.auth

import com.example.composetest.login.domain.repository.AuthRepository

class LoginUseCase constructor(
    private val authRepository: AuthRepository
) {

    suspend fun login(
        phone: String,
        password: String
    ) =
        authRepository.login(
            password = password,
            phone = phone
        )
}