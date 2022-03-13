package com.example.composetest.login.data.remote.model.auth


import com.example.composetest.login.domain.model.auth.SendOTPResponse
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class SendOTPResponseDTO (
    @SerialName("otpCode")
    val otpCode: kotlin.String? = null,
)

fun SendOTPResponseDTO.toDomain(): SendOTPResponse = SendOTPResponse(
    otpCode = otpCode
)