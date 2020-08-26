package com.digitalcreative.appguru.domain.usecase.assignment

import com.digitalcreative.appguru.data.Result
import com.digitalcreative.appguru.data.model.Assignment
import com.digitalcreative.appguru.data.repository.NetworkRepository
import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject

@ActivityRetainedScoped
class GetAssignmentQuestion @Inject constructor(private val networkRepository: NetworkRepository) {
    suspend operator fun invoke(
        teacherId: String,
        classId: String,
        assignmentId: String,
        sectionId: String
    ): Result<List<Assignment.Section.Question>> {
        return networkRepository.getAssignmentQuestion(teacherId, classId, assignmentId, sectionId)
    }
}