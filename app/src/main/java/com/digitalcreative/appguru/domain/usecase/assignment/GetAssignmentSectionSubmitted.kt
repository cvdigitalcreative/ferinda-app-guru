package com.digitalcreative.appguru.domain.usecase.assignment

import com.digitalcreative.appguru.data.Result
import com.digitalcreative.appguru.data.model.Submitted
import com.digitalcreative.appguru.data.repository.NetworkRepository
import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject

@ActivityRetainedScoped
class GetAssignmentSectionSubmitted @Inject constructor(private val networkRepository: NetworkRepository) {
    suspend operator fun invoke(
        teacherId: String,
        classId: String,
        studentId: String,
        assignmentId: String
    ): Result<Submitted> {
        return networkRepository.getAssignmentSectionSubmitted(
            teacherId,
            classId,
            studentId,
            assignmentId
        )
    }
}