package com.example.composetest.login.domain.use_cases.auth

import com.example.composetest.login.domain.repository.AuthRepository

class CheckOtpUseCase(
    private val authRepository: AuthRepository
) {
    suspend fun start(phoneNum: String, otp:String) = authRepository.checkOtp(phoneNum, otp)
}