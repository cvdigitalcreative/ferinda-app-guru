package com.digitalcreative.appguru.presentation.ui.assignment.section

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.digitalcreative.appguru.data.Result
import com.digitalcreative.appguru.data.model.Submitted
import com.digitalcreative.appguru.domain.usecase.assignment.GetAssignmentSectionSubmitted
import com.digitalcreative.appguru.utils.preferences.UserPreferences
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@ActivityRetainedScoped
class SubmittedViewModel @ViewModelInject constructor(
    private val getSubmittedUseCase: GetAssignmentSectionSubmitted,
    private val preferences: UserPreferences
) : ViewModel() {
    private val mLoading = MutableLiveData<Boolean>()
    val loading = mLoading

    private val mSubmitted = MutableLiveData<Submitted>()
    val submitted = mSubmitted

    private val mSuccessMessage = MutableLiveData<String>()
    val successMessage = mSuccessMessage

    private val mErrorMessage = MutableLiveData<String>()
    val errorMessage = mErrorMessage

    fun getAssignmentSectionSubmitted(classId: String, studentId: String, assignmentId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            mLoading.postValue(true)

            val teacherId = preferences.getString(UserPreferences.KEY_NIP)
            when (val response = getSubmittedUseCase(teacherId, classId, studentId, assignmentId)) {
                is Result.Success -> {
                    mSubmitted.postValue((response.data))
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