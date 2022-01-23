package com.example.composetest.login.domain.model

import com.auth0.android.jwt.JWT

data class Token(
    val jwt: String?,
)

fun Token.toDomain() = TokenDTO(jwt = this.jwt)
fun Token.toRequest() = AuthorizeUserRequest(jwt = this.jwt)

fun decodeUserId(token: String?): String? {
    if (token.isNullOrEmpty()) {
        return null
    }
    val jwt = JWT(token)
    return jwt.claims.get("user_id")?.asString()
}