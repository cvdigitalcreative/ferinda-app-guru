package com.digitalcreative.appguru.domain.usecase.classroom

import com.digitalcreative.appguru.data.Result
import com.digitalcreative.appguru.data.repository.NetworkRepository
import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject

@ActivityRetainedScoped
class EditClassroom @Inject constructor(private val networkRepository: NetworkRepository) {
    suspend operator fun invoke(
        teacherId: String,
        classId: String,
        className: String
    ): Result<String> {
        return networkRepository.editClassName(teacherId, classId, className)
    }
}