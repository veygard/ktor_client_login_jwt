package com.example.composetest.login.presentation.viewmodel.auth

import com.example.composetest.login.data.remote.model.auth.JWTRefreshResponse
import com.example.composetest.login.domain.model.AuthorizeUser
import com.example.composetest.login.domain.model.User

sealed class AuthState {
    object Success : AuthState()
    data class Auth(val isSuccess: Boolean?) : AuthState()
    data class GetUser(val user: User?) : AuthState()
    data class AuthResponse(val loginResponse: JWTRefreshResponse) : AuthState()

}
