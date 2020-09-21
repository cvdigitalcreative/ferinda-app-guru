package com.digitalcreative.appguru.domain.usecase.question

import com.digitalcreative.appguru.data.Result
import com.digitalcreative.appguru.data.repository.NetworkRepository
import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject

@ActivityRetainedScoped
class EditQuestion @Inject constructor(private val networkRepository: NetworkRepository) {
    suspend operator fun invoke(
        teacherId: String,
        classId: String,
        assignmentId: String,
        sectionId: String,
        questionId: String,
        formData: Map<String, String>
    ): Result<String> {
        return networkRepository.editQuestion(
            teacherId,
            classId,
            assignmentId,
            sectionId,
            questionId,
            formData
        )
    }
}