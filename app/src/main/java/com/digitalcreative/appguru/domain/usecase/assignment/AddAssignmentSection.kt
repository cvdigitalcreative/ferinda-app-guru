package com.digitalcreative.appguru.domain.usecase.assignment

import com.digitalcreative.appguru.data.Result
import com.digitalcreative.appguru.data.repository.NetworkRepository
import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject

@ActivityRetainedScoped
class AddAssignmentSection @Inject constructor(private val networkRepository: NetworkRepository) {
    suspend operator fun invoke(
        teacherId: String,
        classId: String,
        assignmentId: String,
        section: String
    ): Result<String> {
        return networkRepository.addAssignmentSection(teacherId, classId, assignmentId, section)
    }
}