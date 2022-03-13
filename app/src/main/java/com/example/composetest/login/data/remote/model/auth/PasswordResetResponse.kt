package com.example.composetest.login.data.remote.model.auth


import com.example.composetest.login.domain.model.auth.ChangePasswordResponse
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PasswordResetResponse(
    @SerialName("isSuccess")
    val isSuccess: kotlin.Boolean? = null,
    @SerialName("msg")
    val msg: kotlin.String? = null
)

fun PasswordResetResponse.toDomain(): ChangePasswordResponse =
    ChangePasswordResponse(isSuccess, msg)