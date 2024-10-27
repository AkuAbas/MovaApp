package com.example.mova.api

sealed class NetworkResponse<T>(val data: T?, val message: String?) {

    class Success<T>(data: T) : NetworkResponse<T>(data, null)

    class Error<T>(message: String) : NetworkResponse<T>(null, message)

    class Loading<Nothing>() : NetworkResponse<Nothing>(null, null)
}