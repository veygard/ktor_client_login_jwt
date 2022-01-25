package com.example.composetest.login.presentation.viewmodel.auth

import android.util.Log
import com.example.composetest.login.data.local.model.DataStoreOperations
import com.example.composetest.login.domain.model.Response
import com.example.composetest.login.domain.use_cases.auth.AuthUseCases
import com.example.composetest.login.presentation.viewmodel.BaseViewModel
import com.example.composetest.login.presentation.viewmodel.LoadingState
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.icerock.moko.mvvm.livedata.LiveData
import dev.icerock.moko.mvvm.livedata.MutableLiveData
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authUseCases: AuthUseCases,
    private val dataStore: DataStoreOperations
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
            Log.d("getUser", "started")
            val jwt = dataStore.readToken().stateIn(this).value

            when (val result = authUseCases.getUser.get(jwt)) {
                is Response.Success -> {
                    Log.d("getUser", "Response.Success")
                    _authState.value = AuthState.GotUser(result.dataValue)
                }
                is Response.Error -> {
                    Log.d("getUser", "Response.Error ")
                    _authState.value = AuthState.NoUser(result.errorValue)
                }
            }
        }
    }

    fun logout(){
        viewModelScope.launch {
            dataStore.clearToken()
        }
    }

    override fun clear() {
        _authState.value = null
    }
}