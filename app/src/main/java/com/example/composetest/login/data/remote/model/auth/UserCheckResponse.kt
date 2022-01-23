package com.example.composetest.login.data.remote.model.auth


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserCheckResponse (
    
    @SerialName("is_verified")
    val isVerified: Boolean? = null,
    
    @SerialName("otp_resend_time")
    val otpResendTime: String? = null

) {

}

