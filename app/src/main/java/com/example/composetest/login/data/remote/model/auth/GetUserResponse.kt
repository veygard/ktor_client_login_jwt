package com.example.composetest.login.data.remote.model.auth


import com.example.composetest.login.domain.model.User
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetUserResponse (

    @SerialName("userId")
    val userId: Int,

    @SerialName("createdAt")
    val createdAt: String,

    @SerialName("updatedAt")
    val updatedAt: String?,
) {

    fun toDomain(): User {
        return User(
            userId = userId,
            createdAt = createdAt,
            updatedAt = updatedAt,
        )
    }
}

