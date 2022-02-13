package com.example.composetest.login.presentation.viewmodel.auth

import com.example.composetest.login.data.remote.model.auth.JWTRefreshResponse
import com.example.composetest.login.domain.model.User
import com.example.composetest.login.domain.model.auth.CheckOTPResponse
import com.example.composetest.login.domain.model.auth.SendOTPResponse

sealed class AuthState {
    object Success : AuthState()
    data class Auth(val isSuccess: Boolean?) : AuthState()
    data class GotUser(val user: User?) : AuthState()
    data class NoUser(val error: String?) : AuthState()
    data class CheckUser(val isFound: Boolean?) : AuthState()
    data class AuthResponse(val loginResponse: JWTRefreshResponse) : AuthState()
    data class SendOtp(val otpResponse: SendOTPResponse) : AuthState()
    data class CheckOtp(val checkOTPResponse: CheckOTPResponse) : AuthState()
}
