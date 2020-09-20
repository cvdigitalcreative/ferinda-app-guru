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

    @POST("guru/{teacher_id}/murid/")
    suspend fun addStudent(
        @Path("teacher_id")
        teacherId: String,

        @Body
        body: RequestBody
    ): BaseResponse<Nothing>

    @POST("guru/{teacher_id}/kelas/{class_id}/tugas/")
    suspend fun addAssignment(
        @Path("teacher_id")
        teacherId: String,

        @Path("class_id")
        classId: String,

        @Body
        body: RequestBody
    ): BaseResponse<Nothing>

    @GET("guru/{teacher_id}/kelas/{class_id}/tugas/{assignment_id}/grup-soal/")
    suspend fun getAssignmentSection(
        @Path("teacher_id")
        teacherId: String,

        @Path("class_id")
        classId: String,

        @Path("assignment_id")
        assignmentId: String
    ): BaseResponse<List<Assignment.Section>>

    @GET("guru/{teacher_id}/kelas/{class_id}/tugas/{assignment_id}/grup-soal/{section_id}/soal/")
    suspend fun getAssignmentQuestion(
        @Path("teacher_id")
        teacherId: String,

        @Path("class_id")
        classId: String,

        @Path("assignment_id")
        assignmentId: String,

        @Path("section_id")
        sectionId: String
    ): BaseResponse<List<Assignment.Section.Question>>

    @POST("guru/{teacher_id}/kelas/{class_id}/tugas/{assignment_id}/grup-soal/")
    suspend fun addAssignmentSection(
        @Path("teacher_id")
        teacherId: String,

        @Path("class_id")
        classId: String,

        @Path("assignment_id")
        assignmentId: String,

        @Body
        body: RequestBody
    ): BaseResponse<Nothing>

    @GET("guru/grup-pilihan-soal/")
    suspend fun getGroupChoiceType(): BaseResponse<List<GroupAnswer>>

    @GET("guru/grup-pilihan-soal/{group_answer_id}/pilihan-soal/")
    suspend fun getGroupChoiceDetail(
        @Path("group_answer_id")
        groupAnswerId: String
    ): BaseResponse<List<Answer>>

    @POST("guru/{teacher_id}/kelas/{class_id}/tugas/{assignment_id}/grup-soal/{group_id}/soal/")
    suspend fun addQuestion(
        @Path("teacher_id")
        teacherId: String,

        @Path("class_id")
        classId: String,

        @Path("assignment_id")
        assignmentId: String,

        @Path("group_id")
        sectionId: String,

        @Body
        body: RequestBody
    ): BaseResponse<Nothing>

    @GET("guru/{teacher_id}/kelas/{class_id}/tugas/{assignment_id}/selesai/")
    suspend fun getAssignmentSubmitted(
        @Path("teacher_id")
        teacherId: String,

        @Path("class_id")
        classId: String,

        @Path("assignment_id")
        assignmentId: String
    ): BaseResponse<List<Student>>

    @GET("guru/{teacher_id}/kelas/{class_id}/murid/{student_id}/tugas/{assignment_id}/soal/selesai/")
    suspend fun getAssignmentSectionSubmitted(
        @Path("teacher_id")
        teacherId: String,

        @Path("class_id")
        classId: String,

        @Path("student_id")
        studentId: String,

        @Path("assignment_id")
        assignmentId: String
    ): BaseResponse<Submitted>

    @GET("guru/{teacher_id}/rapot/indikator/")
    suspend fun getReportIndicator(
        @Path("teacher_id")
        teacherId: String
    ): BaseResponse<List<Indicator>>

    @GET("guru/semester/")
    suspend fun getAllSemester(): BaseResponse<List<Semester>>

    @GET("guru/{teacher_id}/kelas/{class_id}/murid/")
    suspend fun getStudentsByClass(
        @Path("teacher_id")
        teacherId: String,

        @Path("class_id")
        classId: String
    ): BaseResponse<List<Student>>

    @POST("guru/{teacher_id}/rapot/")
    suspend fun addReport(
        @Path("teacher_id")
        teacherId: String,

        @Body
        body: RequestBody
    ): BaseResponse<Report>

    @GET("guru/{teacher_id}/rapot/{report_id}")
    suspend fun getDetailReport(
        @Path("teacher_id")
        teacherId: String,

        @Path("report_id")
        reportId: String
    ): BaseResponse<List<Indicator>>

    @POST("guru/{teacher_id}/rapot/{report_id}/jawab/")
    suspend fun sendReportAnswer(
        @Path("teacher_id")
        teacherId: String,

        @Path("report_id")
        reportId: String,

        @Body
        body: RequestBody
    ): BaseResponse<Nothing>

    @POST("guru/{teacher_id}/rapot/{report_id}/kesimpulan/")
    suspend fun sendConclusion(
        @Path("teacher_id")
        teacherId: String,

        @Path("report_id")
        reportId: String,

        @Body
        body: RequestBody
    ): BaseResponse<Nothing>

    @POST("guru/{teacher_id}/kelas/{class_id}")
    suspend fun editClassName(
        @Path("teacher_id")
        teacherId: String,

        @Path("class_id")
        classId: String,

        @Body
        body: RequestBody
    ): BaseResponse<Nothing>

    @GET("guru/{teacher_id}/kelas/{class_id}/murid/")
    suspend fun getAllStudent(
        @Path("teacher_id")
        teacherId: String,

        @Path("class_id")
        classId: String
    ): BaseResponse<List<StudentFull>>

    @POST("guru/{teacher_id}/kelas/{class_id}/tugas/{assignment_id}")
    suspend fun editAssignment(
        @Path("teacher_id")
        teacherId: String,

        @Path("class_id")
        classId: String,

        @Path("assignment_id")
        assignmentId: String,

        @Body
        body: RequestBody
    ): BaseResponse<Nothing>
}