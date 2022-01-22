package com.example.composetest.login.data.remote.model.auth


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class UserAuthenticationResponse (
    
    @SerialName("jwt")
    val jwt: kotlin.String? = null,
    
//    @SerialName("refresh_token")
//    val refreshToken: kotlin.String? = null,
//
//    @SerialName("refresh_token_exp")
//    val refreshTokenExp: kotlin.String? = null
)
