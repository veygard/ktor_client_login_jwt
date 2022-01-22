package com.example.composetest.login.data.remote.model.auth


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class SendOTPResponse (
    
    @SerialName("is_sent")
    val isSent: kotlin.Boolean? = null,
    
    @SerialName("otp_resend_time")
    val otpResendTime: kotlin.String? = null

) {

}

