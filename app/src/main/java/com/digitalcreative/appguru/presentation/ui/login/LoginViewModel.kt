package com.digitalcreative.appguru.presentation.ui.login

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.digitalcreative.appguru.data.Result
import com.digitalcreative.appguru.domain.usecase.common.Login
import com.digitalcreative.appguru.utils.helper.Constants
import com.digitalcreative.appguru.utils.preferences.UserPreferences
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@ActivityRetainedScoped
class LoginViewModel @ViewModelInject constructor(
    private val useCase: Login,
    private val preferences: UserPreferences
) : ViewModel() {
    private val mLoading = MutableLiveData<Boolean>()
    val loading = mLoading

    private val mData = MutableLiveData<Boolean>()
    val data = mData

    private val mMessage = MutableLiveData<String>()
    val message = mMessage

    fun isUserLoggedIn(): Boolean {
        return preferences.getString(UserPreferences.KEY_NIP).isNotEmpty()
    }

    fun login(emailNip: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            if (emailNip.isBlank() || password.isBlank()) {
                mMessage.postValue(Constants.EMPTY_INPUT_ERROR)
                return@launch
            }

            mLoading.postValue(true)
            when (val response = useCase(emailNip, password)) {
                is Result.Success -> {
                    preferences.apply {
                        setString(UserPreferences.KEY_NIP, response.data.id)
                    }

                    mData.postValue(true)
                    mLoading.postValue(false)
                }

                is Result.ErrorRequest -> {
                    mMessage.postValue(response.message)
                    mLoading.postValue(false)
                }

                is Result.ErrorInput -> {
                    mMessage.postValue(response.message)
                }
            }
        }
    }
}