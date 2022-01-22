package com.example.composetest.login.presentation.viewmodel.auth

import com.example.composetest.login.data.remote.model.auth.JWTRefreshResponse

sealed class AuthState {
    object Success : AuthState()
    data class Auth(val isSuccess: Boolean?) : AuthState()
    data class AuthResponse(val loginResponse: JWTRefreshResponse) : AuthState()

}
