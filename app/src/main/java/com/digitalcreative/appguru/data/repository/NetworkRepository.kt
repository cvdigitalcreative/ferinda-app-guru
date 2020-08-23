package com.digitalcreative.appguru.data.repository

import android.util.Log
import com.digitalcreative.appguru.api.ApiService
import com.digitalcreative.appguru.data.Result
import com.digitalcreative.appguru.data.model.Teacher
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
}