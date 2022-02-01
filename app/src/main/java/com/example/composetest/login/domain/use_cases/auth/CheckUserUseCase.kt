package com.example.composetest.login.domain.use_cases.auth

import com.example.composetest.login.domain.repository.AuthRepository
import com.example.composetest.login.navigation.AuthFlowEnum

class CheckUserUseCase(
    private val authRepository: AuthRepository
) {
    suspend fun start(phone: String) = authRepository.checkUser(phone)
}