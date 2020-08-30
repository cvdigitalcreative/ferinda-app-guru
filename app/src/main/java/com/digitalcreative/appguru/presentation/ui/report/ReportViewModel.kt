package com.digitalcreative.appguru.presentation.ui.report

import android.net.Uri
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.digitalcreative.appguru.data.Result
import com.digitalcreative.appguru.data.model.Indicator
import com.digitalcreative.appguru.data.model.Report
import com.digitalcreative.appguru.data.model.Semester
import com.digitalcreative.appguru.data.model.Student
import com.digitalcreative.appguru.domain.usecase.report.*
import com.digitalcreative.appguru.utils.helper.Constants
import com.digitalcreative.appguru.utils.helper.MediaHelper
import com.digitalcreative.appguru.utils.preferences.UserPreferences
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File

@ActivityRetainedScoped
class ReportViewModel @ViewModelInject constructor(
    private val getSemesterUseCase: GetAllSemester,
    private val getStudentUseCase: GetStudentsByClass,
    private val getIndicatorUseCase: GetReportIndicator,
    private val addReportUseCase: AddReport,
    private val getDetailUseCase: GetDetailReport,
    private val preferences: UserPreferences,
    private val mediaHelper: MediaHelper
) : ViewModel() {
    private val mLoading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = mLoading

    private val mSemester = MutableLiveData<List<Semester>>()
    val semester = mSemester

    private val mStudent = MutableLiveData<List<Student>>()
    val student = mStudent

    private val mIndicator = MutableLiveData<List<Indicator>>()
    val indicator = mIndicator

    private val mReport = MutableLiveData<Report>()
    val report = mReport

    private val mDetailReport = MutableLiveData<List<Indicator>>()
    val detailReport: LiveData<List<Indicator>> = mDetailReport

    private val mSuccessMessage = MutableLiveData<String>()
    val successMessage = mSuccessMessage

    private val mErrorMessage = MutableLiveData<String>()
    val errorMessage = mErrorMessage

    private val indicatorList = mutableListOf<String>()

    var semesterId = ""

    var studentId = ""

    fun addIndicator(id: String) {
        indicatorList.add(id)
    }

    fun removeIndicator(id: String) {
        indicatorList.remove(id)
    }

    fun createImageFile() = mediaHelper.createImageFile()

    fun processImage(uri: Uri) {
        viewModelScope.launch {
            createImageFile()?.let { file ->
                mediaHelper.copyImage(uri, file)

                val path = mediaHelper.getPath(uri)
                if (path == null) {
                    mErrorMessage.postValue(Constants.UNKNOWN_ERROR)
                }
                preferences.setString(UserPreferences.KEY_CERTIFICATE, file.path)
            }
        }
    }

    fun compressImage(): File {
        val imagePath = preferences.getString(UserPreferences.KEY_CERTIFICATE)
        val image = File(imagePath)
        mediaHelper.compressImage(image)
        return image
    }

    fun clearTempImages() {
        mediaHelper.clearImageInDirectory()
        preferences.remove(UserPreferences.KEY_CERTIFICATE)
    }

    fun addReport(
        classId: String,
        semesterId: String,
        studentId: String
    ) {
        if (semesterId.isBlank() || studentId.isBlank() || indicatorList.isEmpty()) {
            mErrorMessage.postValue(Constants.EMPTY_INPUT_ERROR)
            return
        }

        viewModelScope.launch {
            val certificate = File(preferences.getString(UserPreferences.KEY_CERTIFICATE))

            if (isImageEmpty(certificate)) {
                mErrorMessage.postValue(Constants.EMPTY_IMAGE_ERROR)
                return@launch
            }

            mLoading.postValue(true)
            mediaHelper.compressImage(certificate)

            indicatorList.sort()

            val indicatorsFlatten = indicatorList.joinToString(prefix = "[", postfix = "]")
            val teacherId = preferences.getString(UserPreferences.KEY_NIP)

            launch(Dispatchers.IO) {
                val response = addReportUseCase(
                    teacherId,
                    classId,
                    semesterId,
                    studentId,
                    indicatorsFlatten,
                    certificate
                )

                when (response) {
                    is Result.Success -> {
                        clearTempImages()
                        mReport.postValue((response.data))
                        mLoading.postValue(false)
                    }

                    is Result.ErrorRequest -> {
                        mErrorMessage.postValue(response.message)
                        mLoading.postValue(false)
                    }
                }
            }
        }
    }

    fun getStudentsByClass(classId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            mLoading.postValue(true)

            val teacherId = preferences.getString(UserPreferences.KEY_NIP)
            when (val response = getStudentUseCase(teacherId, classId)) {
                is Result.Success -> {
                    mStudent.postValue((response.data))
                    mLoading.postValue(false)
                }

                is Result.ErrorRequest -> {
                    mErrorMessage.postValue(response.message)
                    mLoading.postValue(false)
                }
            }
        }
    }

    fun getDetailReport(reportId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            mLoading.postValue(true)

            val teacherId = preferences.getString(UserPreferences.KEY_NIP)
            when (val response = getDetailUseCase(teacherId, reportId)) {
                is Result.Success -> {
                    mDetailReport.postValue((response.data))
                    mLoading.postValue(false)
                }

                is Result.ErrorRequest -> {
                    mErrorMessage.postValue(response.message)
                    mLoading.postValue(false)
                }
            }
        }
    }

    fun getAllSemester() {
        viewModelScope.launch(Dispatchers.IO) {
            mLoading.postValue(true)

            when (val response = getSemesterUseCase()) {
                is Result.Success -> {
                    mSemester.postValue((response.data))
                    mLoading.postValue(false)
                }

                is Result.ErrorRequest -> {
                    mErrorMessage.postValue(response.message)
                    mLoading.postValue(false)
                }
            }
        }
    }

    fun getReportIndicator() {
        viewModelScope.launch(Dispatchers.IO) {
            mLoading.postValue(true)

            val teacherId = preferences.getString(UserPreferences.KEY_NIP)
            when (val response = getIndicatorUseCase(teacherId)) {
                is Result.Success -> {
                    mIndicator.postValue((response.data))
                    mLoading.postValue(false)
                }

                is Result.ErrorRequest -> {
                    mErrorMessage.postValue(response.message)
                    mLoading.postValue(false)
                }
            }
        }
    }

    private fun isImageEmpty(image: File): Boolean {
        return !image.exists()
    }
}