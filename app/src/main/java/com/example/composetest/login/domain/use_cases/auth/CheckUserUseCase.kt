package com.example.composetest.login.domain.use_cases.auth

import com.example.composetest.login.domain.repository.AuthRepository

class CheckUserUseCase(
    private val authRepository: AuthRepository
) {
    suspend fun start(phone: String) = authRepository.checkUser(phone)
}