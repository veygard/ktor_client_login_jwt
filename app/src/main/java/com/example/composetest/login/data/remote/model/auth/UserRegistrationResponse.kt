package com.example.composetest.login.data.remote.model.auth


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserRegistrationResponse (
    @SerialName("message")
    val message: kotlin.String? = null,
)