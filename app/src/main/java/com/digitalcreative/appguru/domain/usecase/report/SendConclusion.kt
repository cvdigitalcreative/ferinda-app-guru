package com.digitalcreative.appguru.domain.usecase.report

import com.digitalcreative.appguru.data.Result
import com.digitalcreative.appguru.data.repository.NetworkRepository
import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject

@ActivityRetainedScoped
class SendConclusion @Inject constructor(private val networkRepository: NetworkRepository) {
    suspend operator fun invoke(
        teacherId: String,
        reportId: String,
        conclusion: String
    ): Result<String> {
        return networkRepository.sendConclusion(teacherId, reportId, conclusion)
    }
}