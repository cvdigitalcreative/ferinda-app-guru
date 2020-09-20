package com.digitalcreative.appguru.domain.usecase.section

import com.digitalcreative.appguru.data.Result
import com.digitalcreative.appguru.data.repository.NetworkRepository
import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject

@ActivityRetainedScoped
class EditAssignmentSection @Inject constructor(private val networkRepository: NetworkRepository) {
    suspend operator fun invoke(
        teacherId: String,
        classId: String,
        assignmentId: String,
        sectionId: String,
        section: String
    ): Result<String> {
        return networkRepository.editSection(teacherId, classId, assignmentId, sectionId, section)
    }
}