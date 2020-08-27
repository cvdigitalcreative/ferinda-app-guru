package com.digitalcreative.appguru.presentation.ui.question

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.digitalcreative.appguru.data.Result
import com.digitalcreative.appguru.data.model.Answer
import com.digitalcreative.appguru.data.model.Assignment
import com.digitalcreative.appguru.data.model.GroupAnswer
import com.digitalcreative.appguru.domain.usecase.question.GetAssignmentChoiceDetail
import com.digitalcreative.appguru.domain.usecase.question.GetAssignmentQuestion
import com.digitalcreative.appguru.domain.usecase.question.GetAssignmentQuestionChoice
import com.digitalcreative.appguru.utils.preferences.UserPreferences
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@ActivityRetainedScoped
class QuestionViewModel @ViewModelInject constructor(
    private val getQuestionUseCase: GetAssignmentQuestion,
    private val getChoiceUseCase: GetAssignmentQuestionChoice,
    private val getAnswerUseCase: GetAssignmentChoiceDetail,
    private val preferences: UserPreferences
) : ViewModel() {
    private val mLoading = MutableLiveData<Boolean>()
    val loading = mLoading

    private val mQuestion = MutableLiveData<List<Assignment.Section.Question>>()
    val question = mQuestion

    private val mChoice = MutableLiveData<List<GroupAnswer>>()
    val choice = mChoice

    private val mAnswer = MutableLiveData<List<Answer>>()
    val answer = mAnswer

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

    fun getAssignmentQuestionChoice() {
        viewModelScope.launch(Dispatchers.IO) {
            mLoading.postValue(true)

            when (val response = getChoiceUseCase()) {
                is Result.Success -> {
                    mChoice.postValue((response.data))
                    mLoading.postValue(false)
                }

                is Result.ErrorRequest -> {
                    mErrorMessage.postValue(response.message)
                    mLoading.postValue(false)
                }
            }
        }
    }

    fun getAssignmentQuestionChoiceDetail(groupAnswerId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            mLoading.postValue(true)

            when (val response = getAnswerUseCase(groupAnswerId)) {
                is Result.Success -> {
                    mAnswer.postValue((response.data))
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