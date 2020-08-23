package com.digitalcreative.appguru.domain.usecase.assignment

import com.digitalcreative.appguru.data.Result
import com.digitalcreative.appguru.data.model.Assignment
import com.digitalcreative.appguru.data.repository.NetworkRepository
import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject

@ActivityRetainedScoped
class GetAssignmentByClassroom @Inject constructor(private val networkRepository: NetworkRepository) {
    suspend operator fun invoke(teacherId: String, classId: String): Result<List<Assignment>> {
        return networkRepository.getAssignmentByClassroom(teacherId, classId)
    }
}