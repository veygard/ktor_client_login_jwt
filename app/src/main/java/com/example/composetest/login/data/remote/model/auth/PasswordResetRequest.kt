package com.example.composetest.login.data.remote.model.auth


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class PasswordResetRequest (
    @SerialName("password")
    val password: kotlin.String? = null
)