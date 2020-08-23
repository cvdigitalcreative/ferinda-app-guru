package com.digitalcreative.appguru.api

import com.digitalcreative.appguru.data.model.*
import com.digitalcreative.appguru.data.response.BaseResponse
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {

    @POST("guru/login/")
    suspend fun login(
        @Body
        body: RequestBody
    ): BaseResponse<Teacher>

    @GET("guru/{teacher_id}/kelas/")
    suspend fun getAllClassroom(
        @Path("teacher_id")
        teacherId: String
    ): BaseResponse<List<Classroom>>

    @POST("guru/{teacher_id}/kelas/")
    suspend fun addClassroom(
        @Path("teacher_id")
        teacherId: String,

        @Body
        body: RequestBody
    ): BaseResponse<Nothing>

    @GET("guru/{teacher_id}/kelas/{class_id}/tugas/")
    suspend fun getAssignmentByClassroom(
        @Path("teacher_id")
        teacherId: String,

        @Path("class_id")
        classId: String
    ): BaseResponse<List<Assignment>>

    @GET("guru/jenis-kelamin/")
    suspend fun getAllGender(): BaseResponse<List<Gender>>

    @GET("guru/agama/")
    suspend fun getAllReligion(): BaseResponse<List<Religion>>
}