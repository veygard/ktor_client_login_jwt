package com.example.composetest.login.data.remote.model.auth


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class JWTRefreshRequest (
    
    @SerialName("device_id")
    val deviceId: kotlin.String? = null,
    
    @SerialName("refresh_token")
    val refreshToken: kotlin.String? = null

) {

}

