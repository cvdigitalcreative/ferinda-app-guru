package com.digitalcreative.appguru.domain.usecase.student

import com.digitalcreative.appguru.data.Result
import com.digitalcreative.appguru.data.repository.NetworkRepository
import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject

@ActivityRetainedScoped
class EditStudent @Inject constructor(private val networkRepository: NetworkRepository) {
    suspend operator fun invoke(
        teacherId: String,
        classId: String,
        studentId: String,
        formData: Map<String, String>
    ): Result<String> {
        return networkRepository.editStudent(teacherId, classId, studentId, formData)
    }
}