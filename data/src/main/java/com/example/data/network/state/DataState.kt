package com.example.data.network.state

sealed class DataState<out T> {
    data class Success<out T>(val data: T) : DataState<T>()
    data class Error(val exception: String) : DataState<Nothing>()
}