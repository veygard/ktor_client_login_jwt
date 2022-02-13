package com.example.composetest.login.data.remote.model.auth


import com.example.composetest.login.domain.model.auth.CreateUserResponse
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserRegistrationResponse (
    @SerialName("isSuccess")
    val isSuccess: kotlin.Boolean? = null,
    @SerialName("msg")
    val message: kotlin.String? = null,
)

fun UserRegistrationResponse.toDomain(): CreateUserResponse = CreateUserResponse(isSuccess, message)