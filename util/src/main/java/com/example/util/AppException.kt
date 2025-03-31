package com.example.util


import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

sealed class AppException(message: String) : Exception(message) {
    data class ApiError(val code: Int, val errorMessage: String) : AppException("API Error $code: $errorMessage")
    data class NullPointerExceptionError(val errorMessage: String = "Response body is null") : AppException(errorMessage)
    data class NetworkError(val errorMessage: String = "No internet connection or server unreachable") : AppException(errorMessage)
    data class UnexpectedError(val errorMessage: String = "Something went wrong") : AppException(errorMessage)
}


fun Exception.toAppException(): AppException {
    return when (this) {
        is UnknownHostException -> AppException.NetworkError()
        is SocketTimeoutException -> AppException.NetworkError()
        is ConnectException -> AppException.NetworkError()
        is HttpException -> AppException.ApiError(this.code(), this.message())
        else -> AppException.UnexpectedError(this.message ?: "Unexpected error occurred")
    }
}