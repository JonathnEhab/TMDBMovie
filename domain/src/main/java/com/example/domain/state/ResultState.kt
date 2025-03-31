package com.example.domain.state

sealed class ResultState<out T> {
    data class Success<T>(val data: T) : ResultState<T>()
    data class Error(val message: String, val exception: Throwable? = null) : ResultState<Nothing>()
}
