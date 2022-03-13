package com.example.composetest.login.domain.use_cases.auth


data class AuthUseCases(
    val loginUseCase: LoginUseCase,
    val getUser: GetUserUseCase,
    val checkUserUseCase: CheckUserUseCase,
    val sendOtpUseCase: SendOtpUseCase,
    val checkOtpUseCase: CheckOtpUseCase,
    val changePasswordUseCase:ChangePasswordUseCase,
    val createUserUseCase: CreateUserUseCase
)