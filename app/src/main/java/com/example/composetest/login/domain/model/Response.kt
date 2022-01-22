package com.example.composetest.login.domain.model

import kotlinx.serialization.Serializable

sealed class Response<out T> {
    data class Success<out T>( val dataValue: T) : Response<T>()
    data class Error(val errorValue: String?) : Response<Nothing>()

    fun isSuccess(): Boolean = this is Success
    fun getData() = (this as Success).dataValue

    fun isError(): Boolean = this is Error
    fun getError() = (this as Error).errorValue
}

@Serializable
data class ErrorResponse(
    val errorCode: Int? = 401,   //if by errorCode you mean the http status code is not really necessary to include here as you already know it from the validateResponse
    val errorMessage: String
)