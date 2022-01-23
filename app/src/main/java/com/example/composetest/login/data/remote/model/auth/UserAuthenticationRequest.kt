package com.example.composetest.login.data.remote.model.auth


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserAuthenticationRequest (
    

    @SerialName("phoneNum")
    val mobile: kotlin.String? = null,
    
    @SerialName("password")
    val password: kotlin.String? = null

) {

}

