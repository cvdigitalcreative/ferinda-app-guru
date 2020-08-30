package com.digitalcreative.appguru.domain.usecase.report

import com.digitalcreative.appguru.data.Result
import com.digitalcreative.appguru.data.model.Indicator
import com.digitalcreative.appguru.data.repository.NetworkRepository
import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject

@ActivityRetainedScoped
class GetDetailReport @Inject constructor(private val networkRepository: NetworkRepository) {
    suspend operator fun invoke(teacherId: String, reportId: String): Result<List<Indicator>> {
        return networkRepository.getDetailReport(teacherId, reportId)
    }
}