package com.digitalcreative.appguru.domain.usecase.report

import com.digitalcreative.appguru.data.Result
import com.digitalcreative.appguru.data.model.Semester
import com.digitalcreative.appguru.data.repository.NetworkRepository
import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject

@ActivityRetainedScoped
class GetAllSemester @Inject constructor(private val networkRepository: NetworkRepository) {
    suspend operator fun invoke(): Result<List<Semester>> {
        return networkRepository.getAllSemester()
    }
}