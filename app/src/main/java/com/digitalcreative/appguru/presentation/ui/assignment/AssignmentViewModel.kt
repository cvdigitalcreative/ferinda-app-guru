package com.digitalcreative.appguru.presentation.ui.assignment

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.digitalcreative.appguru.data.Result
import com.digitalcreative.appguru.data.model.Assignment
import com.digitalcreative.appguru.domain.usecase.assignment.GetAssignmentByClassroom
import com.digitalcreative.appguru.utils.preferences.UserPreferences
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@ActivityRetainedScoped
class AssignmentViewModel @ViewModelInject constructor(
    private val getAssignmentUseCase: GetAssignmentByClassroom,
    private val preferences: UserPreferences
) : ViewModel() {
    private val mLoading = MutableLiveData<Boolean>()
    val loading = mLoading

    private val mAssignment = MutableLiveData<List<Assignment>>()
    val assignment = mAssignment

    private val mErrorMessage = MutableLiveData<String>()
    val errorMessage = mErrorMessage

    fun getAssignmentByClassroom(classId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            mLoading.postValue(true)

            val teacherId = preferences.getString(UserPreferences.KEY_NIP)
            when (val response = getAssignmentUseCase(teacherId, classId)) {
                is Result.Success -> {
                    mAssignment.postValue((response.data))
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