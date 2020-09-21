package com.digitalcreative.appguru.presentation.ui.question

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.digitalcreative.appguru.data.Result
import com.digitalcreative.appguru.data.model.Assignment
import com.digitalcreative.appguru.domain.usecase.question.AddQuestion
import com.digitalcreative.appguru.domain.usecase.question.EditQuestion
import com.digitalcreative.appguru.domain.usecase.question.GetAssignmentQuestion
import com.digitalcreative.appguru.utils.helper.Constants
import com.digitalcreative.appguru.utils.preferences.UserPreferences
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@ActivityRetainedScoped
class QuestionViewModel @ViewModelInject constructor(
    private val getQuestionUseCase: GetAssignmentQuestion,
    private val addQuestionUseCase: AddQuestion,
    private val editQuestionUseCase: EditQuestion,
    private val preferences: UserPreferences
) : ViewModel() {
    private val mLoading = MutableLiveData<Boolean>()
    val loading = mLoading

    private val mQuestion = MutableLiveData<List<Assignment.Section.Question>>()
    val question = mQuestion

    private val mSuccessMessage = MutableLiveData<String>()
    val successMessage = mSuccessMessage

    private val mErrorMessage = MutableLiveData<String>()
    val errorMessage = mErrorMessage

    fun getAssignmentQuestion(classId: String, assignmentId: String, sectionId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            mLoading.postValue(true)

            val teacherId = preferences.getString(UserPreferences.KEY_NIP)
            when (val response = getQuestionUseCase(teacherId, classId, assignmentId, sectionId)) {
                is Result.Success -> {
                    mQuestion.postValue((response.data))
                    mLoading.postValue(false)
                }

                is Result.ErrorRequest -> {
                    mErrorMessage.postValue(response.message)
                    mLoading.postValue(false)
                }
            }
        }
    }

    fun addQuestion(
        classId: String,
        assignmentId: String,
        sectionId: String,
        question: String,
        choicesValue: List<String>
    ) {
        if (question.isBlank() || choicesValue.any { it.isBlank() }) {
            mErrorMessage.postValue(Constants.EMPTY_INPUT_ERROR)
            return
        }

        viewModelScope.launch(Dispatchers.IO) {
            mLoading.postValue(true)

            val teacherId = preferences.getString(UserPreferences.KEY_NIP)
            val choicesValueFlatten = choicesValue.joinToString(prefix = "[", postfix = "]")
            when (val response =
                addQuestionUseCase(
                    teacherId,
                    classId,
                    assignmentId,
                    sectionId,
                    question,
                    choicesValueFlatten
                )) {
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

    fun editQuestion(
        classId: String,
        assignmentId: String,
        sectionId: String,
        questionId: String,
        question: String,
        choicesValue: List<String>
    ) {
        if (question.isBlank() || choicesValue.any { it.isBlank() }) {
            mErrorMessage.postValue(Constants.EMPTY_INPUT_ERROR)
            return
        }

        viewModelScope.launch(Dispatchers.IO) {
            mLoading.postValue(true)

            val teacherId = preferences.getString(UserPreferences.KEY_NIP)
            val choicesValueFlatten = choicesValue.joinToString(prefix = "[", postfix = "]")
            val formData = mapOf(
                "soal" to question,
                "bobot_pilihan" to choicesValueFlatten
            )
            when (val response =
                editQuestionUseCase(
                    teacherId,
                    classId,
                    assignmentId,
                    sectionId,
                    questionId,
                    formData
                )) {
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
}