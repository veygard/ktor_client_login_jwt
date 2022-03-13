package com.example.composetest.login.data.remote.model.auth


import com.example.composetest.login.domain.model.auth.CheckOTPResponse
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class CheckOTPResponseDTO(
    @SerialName("result")
    val result: kotlin.Boolean? = null,

    @SerialName("expired")
    val expired: kotlin.Boolean? = null,

    @SerialName("message")
    val message: kotlin.String? = null
)

fun CheckOTPResponseDTO.toDomain() : CheckOTPResponse = CheckOTPResponse(
    result, expired, message
)