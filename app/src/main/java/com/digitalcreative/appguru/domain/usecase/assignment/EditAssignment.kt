package com.digitalcreative.appguru.domain.usecase.assignment

import com.digitalcreative.appguru.data.Result
import com.digitalcreative.appguru.data.repository.NetworkRepository
import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject

@ActivityRetainedScoped
class EditAssignment @Inject constructor(private val networkRepository: NetworkRepository) {
    suspend operator fun invoke(
        teacherId: String,
        classId: String,
        assignmentId: String,
        assignmentTitle: String,
        assignmentDescription: String
    ): Result<String> {
        return networkRepository.editAssignment(
            teacherId,
            classId,
            assignmentId,
            assignmentTitle,
            assignmentDescription
        )
    }
}