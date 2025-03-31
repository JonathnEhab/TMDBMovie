package com.example.util

import retrofit2.HttpException
import java.io.IOException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.net.ssl.SSLException

object ExceptionHandler {

    fun handleException(e: Throwable): String {
        return when (e) {
            is UnknownHostException, is SocketTimeoutException, is ConnectException, is
            SSLException, is IOException -> AppException.NetworkError().errorMessage

            is HttpException -> {
                val errorMessage = when (e.code()) {
                    400 -> "Bad Request"
                    401 -> "Unauthorized"
                    403 -> "Forbidden"
                    404 -> "Not Found"
                    408 -> "Request Timeout"
                    429 -> "Too Many Requests"
                    500 -> "Internal Server Error"
                    503 -> "Service Unavailable"
                    else -> "Something went wrong"
                }
                AppException.ApiError(e.code(), errorMessage).errorMessage
            }

            else -> AppException.UnexpectedError().errorMessage
        }
    }
}