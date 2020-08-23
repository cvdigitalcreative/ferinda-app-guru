package com.digitalcreative.appguru.domain.usecase.common

import com.digitalcreative.appguru.data.Result
import com.digitalcreative.appguru.data.model.Gender
import com.digitalcreative.appguru.data.repository.NetworkRepository
import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject

@ActivityRetainedScoped
class GetAllGender @Inject constructor(private val networkRepository: NetworkRepository) {
    suspend operator fun invoke(): Result<List<Gender>> {
        return networkRepository.getAllGender()
    }
}