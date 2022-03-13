package com.example.composetest.login.data.remote.model.auth


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class CheckOTPRequest (
    @SerialName("phoneNum")
    val phoneNum: kotlin.String? = null,
    @SerialName("otpCode")
    val otpCode: kotlin.String? = null
)