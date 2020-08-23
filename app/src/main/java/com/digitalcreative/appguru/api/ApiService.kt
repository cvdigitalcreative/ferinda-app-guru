package com.digitalcreative.appguru.api

import com.digitalcreative.appguru.data.model.Assignment
import com.digitalcreative.appguru.data.model.Classroom
import com.digitalcreative.appguru.data.model.Teacher
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

    @GET("guru/{teacher_id}/kelas/{class_id}/tugas/")
    suspend fun getAssignmentByClassroom(
        @Path("teacher_id")
        teacherId: String,

        @Path("class_id")
        classId: String
    ): BaseResponse<List<Assignment>>
}