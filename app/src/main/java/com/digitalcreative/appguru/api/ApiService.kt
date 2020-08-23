package com.digitalcreative.appguru.api

import com.digitalcreative.appguru.data.model.Teacher
import com.digitalcreative.appguru.data.response.BaseResponse
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    @POST("guru/login/")
    suspend fun login(
        @Body
        body: RequestBody
    ): BaseResponse<Teacher>
}