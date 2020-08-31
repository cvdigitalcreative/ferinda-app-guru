package com.digitalcreative.appguru.data.repository

import android.util.Log
import com.digitalcreative.appguru.api.ApiService
import com.digitalcreative.appguru.data.Result
import com.digitalcreative.appguru.data.model.*
import com.digitalcreative.appguru.utils.helper.Constants.CONNECTION_ERROR
import com.digitalcreative.appguru.utils.helper.Constants.STATUS_SUCCESS
import com.digitalcreative.appguru.utils.helper.Constants.UNKNOWN_ERROR
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.net.ConnectException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NetworkRepository @Inject constructor(private val service: ApiService) {

    suspend fun login(emailNip: String, password: String): Result<Teacher> {
        return try {
            val body = MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("email_nip", emailNip)
                .addFormDataPart("password", password)
                .build()
            val response = service.login(body)
            if (response.status == STATUS_SUCCESS) {
                Result.Success(response.data)
            } else {
                Result.ErrorRequest(response.message)
            }
        } catch (e: ConnectException) {
            Log.e("NetworkRepository", "Login -> ${e.localizedMessage}")
            Result.ErrorRequest(CONNECTION_ERROR)
        } catch (e: Exception) {
            Log.e("NetworkRepository", "Login -> ${e.localizedMessage}")
            Result.ErrorRequest(UNKNOWN_ERROR)
        }
    }

    suspend fun getAllClassroom(teacherId: String): Result<List<Classroom>> {
        return try {
            val response = service.getAllClassroom(teacherId)
            if (response.status == STATUS_SUCCESS) {
                Result.Success(response.data)
            } else {
                Result.ErrorRequest(response.message)
            }
        } catch (e: ConnectException) {
            Log.e("NetworkRepository", "GetAllClassroom -> ${e.localizedMessage}")
            Result.ErrorRequest(CONNECTION_ERROR)
        } catch (e: Exception) {
            Log.e("NetworkRepository", "GetAllClassroom -> ${e.localizedMessage}")
            Result.ErrorRequest(UNKNOWN_ERROR)
        }
    }

    suspend fun addClassroom(teacherId: String, className: String): Result<String> {
        return try {
            val body = MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("nama_kelas", className)
                .build()
            val response = service.addClassroom(teacherId, body)
            if (response.status == STATUS_SUCCESS) {
                Result.Success(response.message)
            } else {
                Result.ErrorRequest(response.message)
            }
        } catch (e: ConnectException) {
            Log.e("NetworkRepository", "AddClassroom -> ${e.localizedMessage}")
            Result.ErrorRequest(CONNECTION_ERROR)
        } catch (e: Exception) {
            Log.e("NetworkRepository", "AddClassroom -> ${e.localizedMessage}")
            Result.ErrorRequest(UNKNOWN_ERROR)
        }
    }

    suspend fun getAssignmentByClassroom(
        teacherId: String,
        classId: String
    ): Result<List<Assignment>> {
        return try {
            val response = service.getAssignmentByClassroom(teacherId, classId)
            if (response.status == STATUS_SUCCESS) {
                Result.Success(response.data)
            } else {
                Result.ErrorRequest(response.message)
            }
        } catch (e: ConnectException) {
            Log.e("NetworkRepository", "GetAssignmentByClassroom -> ${e.localizedMessage}")
            Result.ErrorRequest(CONNECTION_ERROR)
        } catch (e: Exception) {
            Log.e("NetworkRepository", "GetAssignmentByClassroom -> ${e.localizedMessage}")
            Result.ErrorRequest(UNKNOWN_ERROR)
        }
    }

    suspend fun getAllGender(): Result<List<Gender>> {
        return try {
            val response = service.getAllGender()
            if (response.status == STATUS_SUCCESS) {
                Result.Success(response.data)
            } else {
                Result.ErrorRequest(response.message)
            }
        } catch (e: ConnectException) {
            Log.e("NetworkRepository", "GetAllGender -> ${e.localizedMessage}")
            Result.ErrorRequest(CONNECTION_ERROR)
        } catch (e: Exception) {
            Log.e("NetworkRepository", "GetAllGender -> ${e.localizedMessage}")
            Result.ErrorRequest(UNKNOWN_ERROR)
        }
    }

    suspend fun getAllReligion(): Result<List<Religion>> {
        return try {
            val response = service.getAllReligion()
            if (response.status == STATUS_SUCCESS) {
                Result.Success(response.data)
            } else {
                Result.ErrorRequest(response.message)
            }
        } catch (e: ConnectException) {
            Log.e("NetworkRepository", "GetAllReligion -> ${e.localizedMessage}")
            Result.ErrorRequest(CONNECTION_ERROR)
        } catch (e: Exception) {
            Log.e("NetworkRepository", "GetAllReligion -> ${e.localizedMessage}")
            Result.ErrorRequest(UNKNOWN_ERROR)
        }
    }

    suspend fun addStudent(teacherId: String, formData: Map<String, String>): Result<String> {
        return try {
            val body = MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("nis", formData["nis"] ?: "")
                .addFormDataPart("email", formData["email"] ?: "")
                .addFormDataPart("nama", formData["name"] ?: "")
                .addFormDataPart("jenis_kelamin", formData["gender"] ?: "")
                .addFormDataPart("agama", formData["religion"] ?: "")
                .addFormDataPart("tempat_lahir", formData["birthPlace"] ?: "")
                .addFormDataPart("tanggal_lahir", formData["birthDate"] ?: "")
                .addFormDataPart("telepon", formData["phone"] ?: "")
                .addFormDataPart("alamat", formData["address"] ?: "")
                .addFormDataPart("id_kelas", formData["classroom"] ?: "")
                .build()
            val response = service.addStudent(teacherId, body)
            if (response.status == STATUS_SUCCESS) {
                Result.Success(response.message)
            } else {
                Result.ErrorRequest(response.message)
            }
        } catch (e: ConnectException) {
            Log.e("NetworkRepository", "AddStudent -> ${e.localizedMessage}")
            Result.ErrorRequest(CONNECTION_ERROR)
        } catch (e: Exception) {
            Log.e("NetworkRepository", "AddStudent -> ${e.localizedMessage}")
            Result.ErrorRequest(UNKNOWN_ERROR)
        }
    }

    suspend fun addAssignment(
        teacherId: String,
        classId: String,
        title: String,
        description: String
    ): Result<String> {
        return try {
            val body = MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("nama_tugas", title)
                .addFormDataPart("deskripsi_tugas", description)
                .build()
            val response = service.addAssignment(teacherId, classId, body)
            if (response.status == STATUS_SUCCESS) {
                Result.Success(response.message)
            } else {
                Result.ErrorRequest(response.message)
            }
        } catch (e: ConnectException) {
            Log.e("NetworkRepository", "AddAssignment -> ${e.localizedMessage}")
            Result.ErrorRequest(CONNECTION_ERROR)
        } catch (e: Exception) {
            Log.e("NetworkRepository", "AddAssignment -> ${e.localizedMessage}")
            Result.ErrorRequest(UNKNOWN_ERROR)
        }
    }

    suspend fun getAssignmentSection(
        teacherId: String,
        classId: String,
        assignmentId: String
    ): Result<List<Assignment.Section>> {
        return try {
            val response = service.getAssignmentSection(teacherId, classId, assignmentId)
            if (response.status == STATUS_SUCCESS) {
                Result.Success(response.data)
            } else {
                Result.ErrorRequest(response.message)
            }
        } catch (e: ConnectException) {
            Log.e("NetworkRepository", "GetAssignmentSection -> ${e.localizedMessage}")
            Result.ErrorRequest(CONNECTION_ERROR)
        } catch (e: Exception) {
            Log.e("NetworkRepository", "GetAssignmentSection -> ${e.localizedMessage}")
            Result.ErrorRequest(UNKNOWN_ERROR)
        }
    }

    suspend fun getAssignmentQuestion(
        teacherId: String,
        classId: String,
        assignmentId: String,
        sectionId: String
    ): Result<List<Assignment.Section.Question>> {
        return try {
            val response =
                service.getAssignmentQuestion(teacherId, classId, assignmentId, sectionId)
            if (response.status == STATUS_SUCCESS) {
                Result.Success(response.data)
            } else {
                Result.ErrorRequest(response.message)
            }
        } catch (e: ConnectException) {
            Log.e("NetworkRepository", "GetAssignmentQuestion -> ${e.localizedMessage}")
            Result.ErrorRequest(CONNECTION_ERROR)
        } catch (e: Exception) {
            Log.e("NetworkRepository", "GetAssignmentQuestion -> ${e.localizedMessage}")
            Result.ErrorRequest(UNKNOWN_ERROR)
        }
    }

    suspend fun addAssignmentSection(
        teacherId: String,
        classId: String,
        assignmentId: String,
        section: String
    ): Result<String> {
        return try {
            val body = MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("grup_soal", section)
                .build()
            val response = service.addAssignmentSection(teacherId, classId, assignmentId, body)
            if (response.status == STATUS_SUCCESS) {
                Result.Success(response.message)
            } else {
                Result.ErrorRequest(response.message)
            }
        } catch (e: ConnectException) {
            Log.e("NetworkRepository", "AddAssignmentSection -> ${e.localizedMessage}")
            Result.ErrorRequest(CONNECTION_ERROR)
        } catch (e: Exception) {
            Log.e("NetworkRepository", "AddAssignmentSection -> ${e.localizedMessage}")
            Result.ErrorRequest(UNKNOWN_ERROR)
        }
    }

    suspend fun getGroupChoiceType(): Result<List<GroupAnswer>> {
        return try {
            val response = service.getGroupChoiceType()
            if (response.status == STATUS_SUCCESS) {
                Result.Success(response.data)
            } else {
                Result.ErrorRequest(response.message)
            }
        } catch (e: ConnectException) {
            Log.e("NetworkRepository", "GetGroupChoiceType -> ${e.localizedMessage}")
            Result.ErrorRequest(CONNECTION_ERROR)
        } catch (e: Exception) {
            Log.e("NetworkRepository", "GetGroupChoiceType -> ${e.localizedMessage}")
            Result.ErrorRequest(UNKNOWN_ERROR)
        }
    }

    suspend fun getGroupChoiceDetail(groupChoiceId: String): Result<List<Answer>> {
        return try {
            val response = service.getGroupChoiceDetail(groupChoiceId)
            if (response.status == STATUS_SUCCESS) {
                Result.Success(response.data)
            } else {
                Result.ErrorRequest(response.message)
            }
        } catch (e: ConnectException) {
            Log.e("NetworkRepository", "GetGroupChoiceDetail -> ${e.localizedMessage}")
            Result.ErrorRequest(CONNECTION_ERROR)
        } catch (e: Exception) {
            Log.e("NetworkRepository", "GetGroupChoiceDetail -> ${e.localizedMessage}")
            Result.ErrorRequest(UNKNOWN_ERROR)
        }
    }

    suspend fun addQuestion(
        teacherId: String,
        classId: String,
        assignmentId: String,
        sectionId: String,
        question: String,
        choicesValue: String
    ): Result<String> {
        return try {
            val body = MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("soal", question)
                .addFormDataPart("id_grup_pilihan", "1")
                .addFormDataPart("id_pilihan_jawaban", "[1, 2]")
                .addFormDataPart("bobot_pilihan_jawaban", choicesValue)
                .build()
            val response = service.addQuestion(teacherId, classId, assignmentId, sectionId, body)
            if (response.status == STATUS_SUCCESS) {
                Result.Success(response.message)
            } else {
                Result.ErrorRequest(response.message)
            }
        } catch (e: ConnectException) {
            Log.e("NetworkRepository", "AddQuestion -> ${e.localizedMessage}")
            Result.ErrorRequest(CONNECTION_ERROR)
        } catch (e: Exception) {
            Log.e("NetworkRepository", "AddQuestion -> ${e.localizedMessage}")
            Result.ErrorRequest(UNKNOWN_ERROR)
        }
    }

    suspend fun getAssignmentSubmitted(
        teacherId: String,
        classId: String,
        assignmentId: String
    ): Result<List<Student>> {
        return try {
            val response = service.getAssignmentSubmitted(teacherId, classId, assignmentId)
            if (response.status == STATUS_SUCCESS) {
                Result.Success(response.data)
            } else {
                Result.ErrorRequest(response.message)
            }
        } catch (e: ConnectException) {
            Log.e("NetworkRepository", "GetAssignmentSubmitted  -> ${e.localizedMessage}")
            Result.ErrorRequest(CONNECTION_ERROR)
        } catch (e: Exception) {
            Log.e("NetworkRepository", "GetAssignmentSubmitted -> ${e.localizedMessage}")
            Result.ErrorRequest(UNKNOWN_ERROR)
        }
    }

    suspend fun getAssignmentSectionSubmitted(
        teacherId: String,
        classId: String,
        studentId: String,
        assignmentId: String
    ): Result<Submitted> {
        return try {
            val response =
                service.getAssignmentSectionSubmitted(teacherId, classId, studentId, assignmentId)
            if (response.status == STATUS_SUCCESS) {
                Result.Success(response.data)
            } else {
                Result.ErrorRequest(response.message)
            }
        } catch (e: ConnectException) {
            Log.e("NetworkRepository", "GetAssignmentSectionSubmitted  -> ${e.localizedMessage}")
            Result.ErrorRequest(CONNECTION_ERROR)
        } catch (e: Exception) {
            Log.e("NetworkRepository", "GetAssignmentSectionSubmitted -> ${e.localizedMessage}")
            Result.ErrorRequest(UNKNOWN_ERROR)
        }
    }

    suspend fun getReportIndicator(teacherId: String): Result<List<Indicator>> {
        return try {
            val response = service.getReportIndicator(teacherId)
            if (response.status == STATUS_SUCCESS) {
                Result.Success(response.data)
            } else {
                Result.ErrorRequest(response.message)
            }
        } catch (e: ConnectException) {
            Log.e("NetworkRepository", "GetReportIndicator  -> ${e.localizedMessage}")
            Result.ErrorRequest(CONNECTION_ERROR)
        } catch (e: Exception) {
            Log.e("NetworkRepository", "GetReportIndicator -> ${e.localizedMessage}")
            Result.ErrorRequest(UNKNOWN_ERROR)
        }
    }

    suspend fun getAllSemester(): Result<List<Semester>> {
        return try {
            val response = service.getAllSemester()
            if (response.status == STATUS_SUCCESS) {
                Result.Success(response.data)
            } else {
                Result.ErrorRequest(response.message)
            }
        } catch (e: ConnectException) {
            Log.e("NetworkRepository", "GetAllSemester  -> ${e.localizedMessage}")
            Result.ErrorRequest(CONNECTION_ERROR)
        } catch (e: Exception) {
            Log.e("NetworkRepository", "GetAllSemester -> ${e.localizedMessage}")
            Result.ErrorRequest(UNKNOWN_ERROR)
        }
    }

    suspend fun getStudentsByClass(teacherId: String, classId: String): Result<List<Student>> {
        return try {
            val response = service.getStudentsByClass(teacherId, classId)
            if (response.status == STATUS_SUCCESS) {
                Result.Success(response.data)
            } else {
                Result.ErrorRequest(response.message)
            }
        } catch (e: ConnectException) {
            Log.e("NetworkRepository", "GetStudentsByClass  -> ${e.localizedMessage}")
            Result.ErrorRequest(CONNECTION_ERROR)
        } catch (e: Exception) {
            Log.e("NetworkRepository", "GetStudentsByClass -> ${e.localizedMessage}")
            Result.ErrorRequest(UNKNOWN_ERROR)
        }
    }

    suspend fun addReport(
        teacherId: String,
        classId: String,
        semesterId: String,
        studentId: String,
        indicators: String,
        certificate: File
    ): Result<Report> {
        return try {
            val requestBody = RequestBody.create(MediaType.parse("image/*"), certificate)
            val body = MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("id_kelas", classId)
                .addFormDataPart("id_semester", semesterId)
                .addFormDataPart("id_indikator", indicators)
                .addFormDataPart("nis", studentId)
                .addFormDataPart("sertifikat", certificate.name, requestBody)
                .build()
            val response = service.addReport(teacherId, body)
            if (response.status == STATUS_SUCCESS) {
                Result.Success(response.data)
            } else {
                Result.ErrorRequest(response.message)
            }
        } catch (e: ConnectException) {
            Log.e("NetworkRepository", "AddReport -> ${e.localizedMessage}")
            Result.ErrorRequest(CONNECTION_ERROR)
        } catch (e: Exception) {
            Log.e("NetworkRepository", "AddReport -> ${e.localizedMessage}")
            Result.ErrorRequest(UNKNOWN_ERROR)
        }
    }

    suspend fun getDetailReport(teacherId: String, reportId: String): Result<List<Indicator>> {
        return try {
            val response = service.getDetailReport(teacherId, reportId)
            if (response.status == STATUS_SUCCESS) {
                Result.Success(response.data)
            } else {
                Result.ErrorRequest(response.message)
            }
        } catch (e: ConnectException) {
            Log.e("NetworkRepository", "GetDetailReport  -> ${e.localizedMessage}")
            Result.ErrorRequest(CONNECTION_ERROR)
        } catch (e: Exception) {
            Log.e("NetworkRepository", "GetDetailReport -> ${e.localizedMessage}")
            Result.ErrorRequest(UNKNOWN_ERROR)
        }
    }

    suspend fun sendReportAnswer(
        teacherId: String,
        reportId: String,
        answers: Map<String, String>
    ): Result<String> {
        return try {
            val body = MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("id_detail_indikator", answers["indicator"] ?: "")
                .addFormDataPart("id_pilihan_jawaban_indikator", answers["answer"] ?: "")
                .build()
            val response = service.sendReportAnswer(teacherId, reportId, body)
            if (response.status == STATUS_SUCCESS) {
                Result.Success(response.message)
            } else {
                Result.ErrorRequest(response.message)
            }
        } catch (e: ConnectException) {
            Log.e("NetworkRepository", "SendReportAnswer -> ${e.localizedMessage}")
            Result.ErrorRequest(CONNECTION_ERROR)
        } catch (e: Exception) {
            Log.e("NetworkRepository", "SendReportAnswer -> ${e.localizedMessage}")
            Result.ErrorRequest(UNKNOWN_ERROR)
        }
    }

    suspend fun sendConclusion(
        teacherId: String,
        reportId: String,
        conclusion: String
    ): Result<String> {
        return try {
            val body = MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("kesimpulan", conclusion)
                .build()
            val response = service.sendConclusion(teacherId, reportId, body)
            if (response.status == STATUS_SUCCESS) {
                Result.Success(response.message)
            } else {
                Result.ErrorRequest(response.message)
            }
        } catch (e: ConnectException) {
            Log.e("NetworkRepository", "SendConclusion -> ${e.localizedMessage}")
            Result.ErrorRequest(CONNECTION_ERROR)
        } catch (e: Exception) {
            Log.e("NetworkRepository", "SendConclusion -> ${e.localizedMessage}")
            Result.ErrorRequest(UNKNOWN_ERROR)
        }
    }
}