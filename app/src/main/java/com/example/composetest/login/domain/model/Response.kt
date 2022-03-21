package com.example.composetest.login.domain.model

import kotlinx.serialization.Serializable

sealed class Response<out T> {
    data class Success<out T>( val dataValue: T) : Response<T>()
    data class Error(val errorType: ServerErrorType) : Response<Nothing>()
}

sealed class ServerErrorType(open val msg:String? = null){
    data class TimeOut(override val msg:String?):ServerErrorType(msg)
    data class ServerException(override val msg:String?):ServerErrorType(msg)
}

@Serializable
data class ErrorResponse(
    val errorCode: Int? = 401,
    val errorMessage: String
)