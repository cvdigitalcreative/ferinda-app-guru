package com.digitalcreative.appguru.presentation.ui.assignment.section

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.digitalcreative.appguru.data.Result
import com.digitalcreative.appguru.data.model.Assignment
import com.digitalcreative.appguru.data.model.Student
import com.digitalcreative.appguru.domain.usecase.assignment.GetAssignmentSubmitted
import com.digitalcreative.appguru.domain.usecase.section.AddAssignmentSection
import com.digitalcreative.appguru.domain.usecase.section.EditAssignmentSection
import com.digitalcreative.appguru.domain.usecase.section.GetAssignmentSection
import com.digitalcreative.appguru.utils.helper.Constants
import com.digitalcreative.appguru.utils.preferences.UserPreferences
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@ActivityRetainedScoped
class SectionViewModel @ViewModelInject constructor(
    private val getSectionUseCase: GetAssignmentSection,
    private val addSectionUseCase: AddAssignmentSection,
    private val editSectionUseCase: EditAssignmentSection,
    private val getSubmittedUseCase: GetAssignmentSubmitted,
    private val preferences: UserPreferences
) : ViewModel() {
    private val mLoading = MutableLiveData<Boolean>()
    val loading = mLoading

    private val mSection = MutableLiveData<List<Assignment.Section>>()
    val section = mSection

    private val mAssignmentSubmitted = MutableLiveData<List<Student>>()
    val assignmentSubmitted = mAssignmentSubmitted

    private val mSuccessMessage = MutableLiveData<String>()
    val successMessage = mSuccessMessage

    private val mErrorMessage = MutableLiveData<String>()
    val errorMessage = mErrorMessage

    fun getAssignmentSection(classId: String, assignmentId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            mLoading.postValue(true)

            val teacherId = preferences.getString(UserPreferences.KEY_NIP)
            when (val response = getSectionUseCase(teacherId, classId, assignmentId)) {
                is Result.Success -> {
                    mSection.postValue((response.data))
                    mLoading.postValue(false)
                }

                is Result.ErrorRequest -> {
                    mErrorMessage.postValue(response.message)
                    mLoading.postValue(false)
                }
            }
        }
    }

    fun addAssignmentSection(classId: String, assignmentId: String, section: String) {
        if (section.isBlank()) {
            mErrorMessage.postValue(Constants.EMPTY_INPUT_ERROR)
            return
        }

        viewModelScope.launch(Dispatchers.IO) {
            mLoading.postValue(true)

            val teacherId = preferences.getString(UserPreferences.KEY_NIP)
            when (val response = addSectionUseCase(teacherId, classId, assignmentId, section)) {
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

    fun editAssignmentSection(
        classId: String,
        assignmentId: String,
        sectionId: String,
        section: String
    ) {
        if (section.isBlank()) {
            mErrorMessage.postValue(Constants.EMPTY_INPUT_ERROR)
            return
        }

        viewModelScope.launch(Dispatchers.IO) {
            mLoading.postValue(true)

            val teacherId = preferences.getString(UserPreferences.KEY_NIP)
            when (val response =
                editSectionUseCase(teacherId, classId, assignmentId, sectionId, section)) {
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

    fun getAssignmentSubmitted(classId: String, assignmentId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            mLoading.postValue(true)

            val teacherId = preferences.getString(UserPreferences.KEY_NIP)
            when (val response = getSubmittedUseCase(teacherId, classId, assignmentId)) {
                is Result.Success -> {
                    val parsedData = response.data.map { item ->
                        item.copy(name = item.name.replace(" ", "+"))
                    }

                    mAssignmentSubmitted.postValue(parsedData)
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