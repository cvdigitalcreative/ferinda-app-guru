package com.digitalcreative.appguru.domain.usecase.student

import com.digitalcreative.appguru.data.Result
import com.digitalcreative.appguru.data.model.StudentFull
import com.digitalcreative.appguru.data.repository.NetworkRepository
import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject

@ActivityRetainedScoped
class GetAllStudent @Inject constructor(private val networkRepository: NetworkRepository) {
    suspend operator fun invoke(teacherId: String, classId: String): Result<List<StudentFull>> {
        return networkRepository.getAllStudent(teacherId, classId)
    }
}