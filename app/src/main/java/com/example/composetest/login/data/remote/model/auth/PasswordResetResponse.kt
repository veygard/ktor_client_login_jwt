package com.example.composetest.login.data.remote.model.auth


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PasswordResetResponse (
    
    @SerialName("msg")
    val msg: kotlin.String? = null

) {

}

