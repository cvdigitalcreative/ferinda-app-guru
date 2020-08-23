package com.digitalcreative.appguru.domain.usecase.section

import com.digitalcreative.appguru.data.Result
import com.digitalcreative.appguru.data.model.Assignment
import com.digitalcreative.appguru.data.repository.NetworkRepository
import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject

@ActivityRetainedScoped
class GetAssignmentSection @Inject constructor(private val networkRepository: NetworkRepository) {
    suspend operator fun invoke(
        teacherId: String,
        classId: String,
        assignmentId: String
    ): Result<List<Assignment.Section>> {
        return networkRepository.getAssignmentSection(teacherId, classId, assignmentId)
    }
}