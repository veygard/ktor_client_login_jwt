package com.example.composetest.login.domain.model.auth


data class CheckOTPResponse(
    val result: kotlin.Boolean? = null,
    val expired: kotlin.Boolean? = null,
    val message: kotlin.String? = null
)

