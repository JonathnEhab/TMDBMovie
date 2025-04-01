package com.example.util


import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

sealed class AppException(message: String) : Exception(message) {
    data class ApiError(val code: Int, val errorMessage: String) : AppException("API Error $code: $errorMessage")
    data class NetworkError(val errorMessage: String = "No internet connection or server unreachable") : AppException(errorMessage)
    data class UnexpectedError(val errorMessage: String = "Something went wrong") : AppException(errorMessage)
}


