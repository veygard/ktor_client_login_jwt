package com.example.composetest.login.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class TokenDTO(
    val jwt: String?,
)

fun TokenDTO.toDomain() = Token(jwt = this.jwt)

