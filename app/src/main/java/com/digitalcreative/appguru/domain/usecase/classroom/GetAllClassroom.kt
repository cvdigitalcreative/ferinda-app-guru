package com.digitalcreative.appguru.domain.usecase.classroom

import com.digitalcreative.appguru.data.Result
import com.digitalcreative.appguru.data.model.Classroom
import com.digitalcreative.appguru.data.repository.NetworkRepository
import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject

@ActivityRetainedScoped
class GetAllClassroom @Inject constructor(private val networkRepository: NetworkRepository) {
    suspend operator fun invoke(teacherId: String): Result<List<Classroom>> {
        return networkRepository.getAllClassroom(teacherId)
    }
}