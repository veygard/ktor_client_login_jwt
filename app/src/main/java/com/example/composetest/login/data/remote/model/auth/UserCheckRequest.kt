package com.example.composetest.login.data.remote.model.auth


import kotlinx.serialization.Serializable

@Serializable
data class UserCheckRequest (
    val phoneNum: kotlin.String? = null
)