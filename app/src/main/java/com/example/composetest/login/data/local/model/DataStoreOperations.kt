package com.example.composetest.login.data.local.model

import kotlinx.coroutines.flow.Flow

interface DataStoreOperations {
    suspend fun saveToken(token: String?)
    fun readSaveToken(): Flow<String>
}