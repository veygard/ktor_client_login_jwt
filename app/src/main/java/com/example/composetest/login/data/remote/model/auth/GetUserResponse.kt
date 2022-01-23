package com.example.composetest.login.data.remote.model.auth


import com.example.composetest.login.domain.model.User
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetUserResponse (

    @SerialName("userId")
    val userId: Int,

    @SerialName("phoneNum")
    val phoneNum: String,

    @SerialName("passwordHash")
    val passwordHash: String,

    @SerialName("createdAt")
    val createdAt: String,

    @SerialName("updatedAt")
    val updatedAt: String?,

    @SerialName("profileId")
    val profileId: Int,
) {

    fun toDomain(): User {
        return User(
            userId = userId,
            phoneNum = phoneNum,
            passwordHash = passwordHash,
            createdAt = createdAt,
            updatedAt = updatedAt,
            profileId = profileId
        )
    }
}

