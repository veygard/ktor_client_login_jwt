package com.example.composetest.login.data.remote.model.auth


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserCheckResponse (
    @SerialName("isFound")
    val isFound: Boolean? = null,
    @SerialName("msg")
    val msg: String? = null
)
