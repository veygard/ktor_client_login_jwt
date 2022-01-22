package com.example.composetest.login.data.remote.model.auth


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserCheckRequest (
    
    @SerialName("mobile")
    val mobile: kotlin.String? = null

) {

}

