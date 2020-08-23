package com.digitalcreative.appguru.data.response

data class BaseResponse<T>(
    val status: String,
    val message: String,
    val data: T
)