package com.digitalcreative.appguru.domain.usecase.report

import com.digitalcreative.appguru.data.Result
import com.digitalcreative.appguru.data.model.Student
import com.digitalcreative.appguru.data.repository.NetworkRepository
import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject

@ActivityRetainedScoped
class GetStudentsByClass @Inject constructor(private val networkRepository: NetworkRepository) {
    suspend operator fun invoke(teacherId: String, classId: String): Result<List<Student>> {
        return networkRepository.getStudentsByClass(teacherId, classId)
    }
}