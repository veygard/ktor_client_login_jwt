package com.example.composetest.login.domain.model.auth

import kotlinx.serialization.SerialName

data class CreateUserResponse(
    val isSuccess: kotlin.Boolean? = null,
    val message: kotlin.String? = null,
)