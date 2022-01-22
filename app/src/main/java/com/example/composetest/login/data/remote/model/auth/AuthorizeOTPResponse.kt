
package com.example.composetest.login.data.remote.model.auth


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable



@Serializable
data class AuthorizeOTPResponse (
    
    @SerialName("jwt")
    val jwt: kotlin.String? = null

) {

}

