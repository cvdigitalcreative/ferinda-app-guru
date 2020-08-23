package com.digitalcreative.appguru.presentation.ui.classroom

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.digitalcreative.appguru.data.Result
import com.digitalcreative.appguru.data.model.Classroom
import com.digitalcreative.appguru.domain.usecase.classroom.GetAllClassroom
import com.digitalcreative.appguru.utils.preferences.UserPreferences
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@ActivityRetainedScoped
class ClassroomViewModel @ViewModelInject constructor(
    private val getClassroomUseCase: GetAllClassroom,
    private val preferences: UserPreferences
) : ViewModel() {
    private val mLoading = MutableLiveData<Boolean>()
    val loading = mLoading

    private val mClassroom = MutableLiveData<List<Classroom>>()
    val classroom = mClassroom

    private val mErrorMessage = MutableLiveData<String>()
    val errorMessage = mErrorMessage

    fun getAllClassroom() {
        viewModelScope.launch(Dispatchers.IO) {
            val teacherId = preferences.getString(UserPreferences.KEY_NIP)

            mLoading.postValue(true)
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