package com.digitalcreative.appguru.presentation.ui.classroom

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.digitalcreative.appguru.data.Result
import com.digitalcreative.appguru.data.model.Classroom
import com.digitalcreative.appguru.domain.usecase.classroom.AddClassroom
import com.digitalcreative.appguru.domain.usecase.classroom.GetAllClassroom
import com.digitalcreative.appguru.utils.helper.Constants
import com.digitalcreative.appguru.utils.preferences.UserPreferences
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@ActivityRetainedScoped
class ClassroomViewModel @ViewModelInject constructor(
    private val getClassroomUseCase: GetAllClassroom,
    private val addClassroomUseCase: AddClassroom,
    private val preferences: UserPreferences
) : ViewModel() {
    private val mLoading = MutableLiveData<Boolean>()
    val loading = mLoading

    private val mClassroom = MutableLiveData<List<Classroom>>()
    val classroom = mClassroom

    private val mSuccessMessage = MutableLiveData<String>()
    val successMessage = mSuccessMessage

    private val mErrorMessage = MutableLiveData<String>()
    val errorMessage = mErrorMessage

    fun getAllClassroom() {
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

    fun addClassroom(className: String) {
        viewModelScope.launch(Dispatchers.IO) {
            if (className.isBlank()) {
                mErrorMessage.postValue(Constants.EMPTY_INPUT_ERROR)
                return@launch
            }

            mLoading.postValue(true)

            val teacherId = preferences.getString(UserPreferences.KEY_NIP)
            when (val response = addClassroomUseCase(teacherId, className)) {
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

    fun logout() {
        viewModelScope.launch {
            preferences.apply {
                remove(UserPreferences.KEY_NIP)
                remove(UserPreferences.KEY_CERTIFICATE)
            }
        }
    }
}