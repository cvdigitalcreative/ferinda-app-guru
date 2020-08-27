package com.digitalcreative.appguru.domain.usecase.question

import com.digitalcreative.appguru.data.Result
import com.digitalcreative.appguru.data.repository.NetworkRepository
import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject

@ActivityRetainedScoped
class AddQuestion @Inject constructor(private val networkRepository: NetworkRepository) {
    suspend operator fun invoke(
        teacherId: String,
        classId: String,
        assignmentId: String,
        groupId: String,
        question: String,
        choicesValue: String
    ): Result<String> {
        return networkRepository.addQuestion(
            teacherId,
            classId,
            assignmentId,
            groupId,
            question,
            choicesValue
        )
    }
}