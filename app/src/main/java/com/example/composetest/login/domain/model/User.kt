package com.example.composetest.login.domain.model


data class User(
    val userId: Int,
    val phoneNum: String,
    val passwordHash: String,
    val createdAt: String,
    val updatedAt: String?,
    val profileId: Int,
)