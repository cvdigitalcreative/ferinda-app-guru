package com.digitalcreative.appguru.presentation.ui.report

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.digitalcreative.appguru.data.Result
import com.digitalcreative.appguru.data.model.Indicator
import com.digitalcreative.appguru.data.model.Semester
import com.digitalcreative.appguru.data.model.Student
import com.digitalcreative.appguru.domain.usecase.report.GetAllSemester
import com.digitalcreative.appguru.domain.usecase.report.GetReportIndicator
import com.digitalcreative.appguru.domain.usecase.report.GetStudentsByClass
import com.digitalcreative.appguru.utils.preferences.UserPreferences
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@ActivityRetainedScoped
class ReportViewModel @ViewModelInject constructor(
    private val getSemesterUseCase: GetAllSemester,
    private val getStudentUseCase: GetStudentsByClass,
    private val getIndicatorUseCase: GetReportIndicator,
    private val preferences: UserPreferences
) : ViewModel() {
    private val mLoading = MutableLiveData<Boolean>()
    val loading = mLoading

    private val mSemester = MutableLiveData<List<Semester>>()
    val semester = mSemester

    private val mStudent = MutableLiveData<List<Student>>()
    val student = mStudent

    private val mIndicator = MutableLiveData<List<Indicator>>()
    val indicator = mIndicator

    private val mSuccessMessage = MutableLiveData<String>()
    val successMessage = mSuccessMessage

    private val mErrorMessage = MutableLiveData<String>()
    val errorMessage = mErrorMessage

    private val indicatorList = mutableListOf<String>()

    var semesterId = ""

    var studentId = ""

    init {
        getAllSemester()
        getReportIndicator()
    }

    fun addIndicator(id: String) {
        indicatorList.add(id)
    }

    fun removeIndicator(id: String) {
        indicatorList.remove(id)
    }

    fun getIndicatorList() = indicatorList

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

    private fun getAllSemester() {
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

    private fun getReportIndicator() {
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
}