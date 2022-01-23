package com.example.composetest.login.data.remote.storage

import com.example.composetest.login.data.remote.storage.TokenDTO


interface DataStorage {

    var tokenDTO: TokenDTO?

    fun clearTokenDTO()

    fun clearAll()

    companion object {
        const val TOKEN = "token"
    }
}

