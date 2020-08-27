package com.digitalcreative.appguru.domain.usecase.question

import com.digitalcreative.appguru.data.Result
import com.digitalcreative.appguru.data.model.Answer
import com.digitalcreative.appguru.data.repository.NetworkRepository
import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject

@ActivityRetainedScoped
class GetAssignmentChoiceDetail @Inject constructor(private val networkRepository: NetworkRepository) {
    suspend operator fun invoke(groupChoiceId: String): Result<List<Answer>> {
        return networkRepository.getGroupChoiceDetail(groupChoiceId)
    }
}