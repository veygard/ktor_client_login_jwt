package com.example.composetest.login.presentation.viewmodel.auth

import android.util.Log
import com.example.composetest.login.domain.model.Response
import com.example.composetest.login.domain.use_cases.auth.AuthUseCases
import com.example.composetest.login.presentation.viewmodel.BaseViewModel
import com.example.composetest.login.presentation.viewmodel.LoadingState
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.icerock.moko.mvvm.livedata.LiveData
import dev.icerock.moko.mvvm.livedata.MutableLiveData
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authUseCases: AuthUseCases
) : BaseViewModel() {

    private val _authState = MutableLiveData<AuthState?>(null)
    val authState: LiveData<AuthState?> = _authState

    fun login(phone: String, password: String) {
        viewModelScope.launch {
            Log.d("AuthFlow", "LoginViewModel login started")

            val result = authUseCases.loginUseCase.login(phone, password)

            when (result) {
                is Response.Success -> {
                    _authState.value = AuthState.Success
                    Log.d("AuthFlow", "LoginViewModel Success: ${result.dataValue}")
                }
                is Response.Error -> {
                    _errorState.value = result.errorValue
                }
            }
            _loadingState.value = LoadingState.Hide
            _authState.value = null
        }
    }

    fun getUser() {
        viewModelScope.launch {
            val result = authUseCases.getUser.get()
            when (result) {
                is Response.Success -> {
                    _authState.value = AuthState.GetUser(result.dataValue)
                }
                is Response.Error -> {
                    _errorState.value = result.errorValue
                }
            }
        }
    }

    override fun clear() {
        _authState.value = null
    }
}