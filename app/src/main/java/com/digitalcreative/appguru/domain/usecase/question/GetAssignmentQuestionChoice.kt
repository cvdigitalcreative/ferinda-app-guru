package com.digitalcreative.appguru.domain.usecase.question

import com.digitalcreative.appguru.data.Result
import com.digitalcreative.appguru.data.model.GroupAnswer
import com.digitalcreative.appguru.data.repository.NetworkRepository
import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject

@ActivityRetainedScoped
class GetAssignmentQuestionChoice @Inject constructor(private val networkRepository: NetworkRepository) {
    suspend operator fun invoke(): Result<List<GroupAnswer>> {
        return networkRepository.getGroupChoiceType()
    }
}