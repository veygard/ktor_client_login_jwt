package com.example.composetest.login.domain.use_cases.auth

import com.example.composetest.login.domain.repository.AuthRepository

class GetUserUseCase(
    private val authRepository: AuthRepository
) {
    suspend fun get() = authRepository.getUser()
}