package com.example.composetest.login.domain.use_cases.auth

import com.example.composetest.login.domain.repository.AuthRepository

class SendOtpUseCase(
    private val authRepository: AuthRepository
) {
    suspend fun start(phoneNum: String) = authRepository.sendOtp(phoneNum)
}