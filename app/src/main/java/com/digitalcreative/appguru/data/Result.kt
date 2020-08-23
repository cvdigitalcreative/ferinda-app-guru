package com.digitalcreative.appguru.data

sealed class Result<out T> {
    object Loading : Result<Nothing>()
    data class Success<T>(val data: T) : Result<T>()
    data class ErrorRequest(val message: String) : Result<Nothing>()
    data class ErrorInput(val message: String) : Result<Nothing>()
}