package com.example.composetest.login.data.remote.model.auth


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class SendOTPRequest (
    @SerialName("phoneNum")
    val phoneNum: kotlin.String? = null
)

