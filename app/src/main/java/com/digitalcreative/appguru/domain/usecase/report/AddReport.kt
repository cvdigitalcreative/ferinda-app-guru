package com.digitalcreative.appguru.domain.usecase.report

import com.digitalcreative.appguru.data.Result
import com.digitalcreative.appguru.data.model.Report
import com.digitalcreative.appguru.data.repository.NetworkRepository
import dagger.hilt.android.scopes.ActivityRetainedScoped
import java.io.File
import javax.inject.Inject

@ActivityRetainedScoped
class AddReport @Inject constructor(private val networkRepository: NetworkRepository) {
    suspend operator fun invoke(
        teacherId: String,
        classId: String,
        semesterId: String,
        studentId: String,
        indicators: String,
        certificate: File
    ): Result<Report> {
        return networkRepository.addReport(
            teacherId,
            classId,
            semesterId,
            studentId,
            indicators,
            certificate
        )
    }
}