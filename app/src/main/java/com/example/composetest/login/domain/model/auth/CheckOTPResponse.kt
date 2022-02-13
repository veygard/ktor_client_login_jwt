package com.example.composetest.login.domain.model.auth


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


data class CheckOTPResponse(
    val result: kotlin.Boolean? = null,
    val expired: kotlin.Boolean? = null,
    val message: kotlin.String? = null
)