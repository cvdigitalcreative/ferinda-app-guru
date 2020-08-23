package com.digitalcreative.appguru.domain.usecase.common

import com.digitalcreative.appguru.data.Result
import com.digitalcreative.appguru.data.model.Teacher
import com.digitalcreative.appguru.data.repository.NetworkRepository
import com.digitalcreative.appguru.utils.crypto.Hash.sha256
import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject

@ActivityRetainedScoped
class Login @Inject constructor(private val networkRepository: NetworkRepository) {
    suspend operator fun invoke(emailNip: String, password: String): Result<Teacher> {
        val hashedPassword = sha256(password)
        return networkRepository.login(emailNip, hashedPassword)
    }
}