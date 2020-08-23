package com.digitalcreative.appguru.presentation.ui.student

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.digitalcreative.appguru.data.Result
import com.digitalcreative.appguru.data.model.Classroom
import com.digitalcreative.appguru.data.model.Gender
import com.digitalcreative.appguru.data.model.Religion
import com.digitalcreative.appguru.domain.usecase.classroom.GetAllClassroom
import com.digitalcreative.appguru.domain.usecase.common.GetAllGender
import com.digitalcreative.appguru.domain.usecase.common.GetAllReligion
import com.digitalcreative.appguru.domain.usecase.student.AddStudent
import com.digitalcreative.appguru.utils.helper.Constants
import com.digitalcreative.appguru.utils.preferences.UserPreferences
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@ActivityRetainedScoped
class StudentViewModel @ViewModelInject constructor(
    private val getGenderUseCase: GetAllGender,
    private val getReligionUseCase: GetAllReligion,
    private val getClassroomUseCase: GetAllClassroom,
    private val addStudentUseCase: AddStudent,
    private val preferences: UserPreferences
) : ViewModel() {
    private val mLoading = MutableLiveData<Boolean>()
    val loading = mLoading

    private val mGender = MutableLiveData<List<Gender>>()
    val gender = mGender

    private val mReligion = MutableLiveData<List<Religion>>()
    val religion = mReligion

    private val mClassroom = MutableLiveData<List<Classroom>>()
    val classroom = mClassroom

    private val mSuccessMessage = MutableLiveData<String>()
    val successMessage = mSuccessMessage

    private val mErrorMessage = MutableLiveData<String>()
    val errorMessage = mErrorMessage

    init {
        getAllGender()
        getAllReligion()
        getAllClassroom()
    }

    fun addStudent(formData: Map<String, String>) {
        if (!isFormValid(formData)) {
            mErrorMessage.postValue(Constants.EMPTY_INPUT_ERROR)
            return
        }

        viewModelScope.launch(Dispatchers.IO) {
            mLoading.postValue(true)

            val teacherId = preferences.getString(UserPreferences.KEY_NIP)
            when (val response = addStudentUseCase(teacherId, formData)) {
                is Result.Success -> {
                    mSuccessMessage.postValue((response.data))
                    mLoading.postValue(false)
                }

                is Result.ErrorRequest -> {
                    mErrorMessage.postValue(response.message)
                    mLoading.postValue(false)
                }
            }
        }
    }

    private fun isFormValid(formData: Map<String, String>): Boolean {
        return formData.entries.none { it.value.isBlank() }
    }

    private fun getAllGender() {
        viewModelScope.launch(Dispatchers.IO) {
            mLoading.postValue(true)

            when (val response = getGenderUseCase()) {
                is Result.Success -> {
                    mGender.postValue((response.data))
                    mLoading.postValue(false)
                }

                is Result.ErrorRequest -> {
                    mErrorMessage.postValue(response.message)
                    mLoading.postValue(false)
                }
            }
        }
    }

    private fun getAllReligion() {
        viewModelScope.launch(Dispatchers.IO) {
            mLoading.postValue(true)

            when (val response = getReligionUseCase()) {
                is Result.Success -> {
                    mReligion.postValue((response.data))
                    mLoading.postValue(false)
                }

                is Result.ErrorRequest -> {
                    mErrorMessage.postValue(response.message)
                    mLoading.postValue(false)
                }
            }
        }
    }

    private fun getAllClassroom() {
        viewModelScope.launch(Dispatchers.IO) {
            mLoading.postValue(true)

            val teacherId = preferences.getString(UserPreferences.KEY_NIP)
            when (val response = getClassroomUseCase(teacherId)) {
                is Result.Success -> {
                    mClassroom.postValue((response.data))
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