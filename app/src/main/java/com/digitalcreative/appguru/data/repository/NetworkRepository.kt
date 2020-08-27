package com.digitalcreative.appguru.data.repository

import android.util.Log
import com.digitalcreative.appguru.api.ApiService
import com.digitalcreative.appguru.data.Result
import com.digitalcreative.appguru.data.model.*
import com.digitalcreative.appguru.utils.helper.Constants.CONNECTION_ERROR
import com.digitalcreative.appguru.utils.helper.Constants.STATUS_SUCCESS
import com.digitalcreative.appguru.utils.helper.Constants.UNKNOWN_ERROR
import okhttp3.MultipartBody
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
}