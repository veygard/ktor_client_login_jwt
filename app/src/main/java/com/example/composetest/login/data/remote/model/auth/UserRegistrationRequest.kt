package com.example.composetest.login.data.remote.model.auth


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserRegistrationRequest (
    
    @SerialName("phoneNumber")
    val phoneNumber: kotlin.String? = null,
    
    @SerialName("password")
    val password: kotlin.String? = null
)