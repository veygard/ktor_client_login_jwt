package com.example.composetest.login.data.remote.storage

import com.example.composetest.login.domain.model.Token
import kotlinx.serialization.Serializable

@Serializable
data class TokenDTO(
    val jwt: String?,
) {
    fun toDomain(): Token {
        return Token(
            jwt = jwt,
        )
    }
}

