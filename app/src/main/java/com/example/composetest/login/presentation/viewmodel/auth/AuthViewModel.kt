package com.example.composetest.login.presentation.viewmodel.auth

import android.util.Log
import com.example.composetest.login.data.local.model.DataStoreOperations
import com.example.composetest.login.domain.model.Response
import com.example.composetest.login.domain.model.ServerErrorType
import com.example.composetest.login.domain.use_cases.auth.AuthUseCases
import com.example.composetest.login.presentation.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.icerock.moko.mvvm.livedata.LiveData
import dev.icerock.moko.mvvm.livedata.MutableLiveData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authUseCases: AuthUseCases,
    private val dataStore: DataStoreOperations
) : BaseViewModel() {

    init {
        Log.d("viewmodel", "vm was created")
    }

    /*тестирование StateFlow + Composable*/
    private val _authStateCompose: MutableStateFlow<AuthState?> = MutableStateFlow(null)
    val authStateCompose: StateFlow<AuthState?> = _authStateCompose

    /*тестирование LiveData + Composable*/
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
                    setStateByErrorType(result.errorType)
                }
            }
        }
    }

    fun checkUser(phone: String) {
        viewModelScope.launch {
            Log.d("checkUser", "started")
            val result = authUseCases.checkUserUseCase.start(phone)

            when (result) {
                is Response.Success -> {
                    Log.d("checkUser", "Response.Success")
                    _authState.value = AuthState.CheckUser(result.dataValue)
                }
                is Response.Error -> {
                    Log.d("checkUser", "Response.Error")
                    setStateByErrorType(result.errorType)
                }
            }
        }
    }

    fun getUser() {
        viewModelScope.launch {
            Log.d("getUser", "started")
            val jwt = dataStore.readToken().stateIn(this).value

            when (val result = authUseCases.getUser.start(jwt)) {
                is Response.Success -> {
                    Log.d("getUser", "Response.Success")
                    _authState.value = AuthState.GotUser(result.dataValue)
                }
                is Response.Error -> {
                    Log.d("getUser", "Response.Error ")
                    setStateByErrorType(result.errorType)
                }
            }
        }
    }

    fun sendOtp(phoneNum: String) {
        viewModelScope.launch {
            when (val result = authUseCases.sendOtpUseCase.start(phoneNum)) {
                is Response.Success -> {
                    _authStateCompose.value = AuthState.SendOtp(result.dataValue)
                }
                is Response.Error -> {
                    setStateByErrorType(result.errorType)
                }
            }
        }
    }

    fun checkOtp(phoneNum: String, otp: String) {
        viewModelScope.launch {
            when (val result = authUseCases.checkOtpUseCase.start(phoneNum, otp)) {

                is Response.Success -> {
                    Log.d(
                        "checkOTP",
                        "AuthViewModel  Response.Success, result ${result.dataValue.result}, expired ${result.dataValue.expired}, message ${result.dataValue.message}"
                    )
                    _authState.value = AuthState.CheckOtp(result.dataValue)
                    _authStateCompose.value = AuthState.CheckOtp(result.dataValue)
                }
                is Response.Error -> {
                    setStateByErrorType(result.errorType)
                }
            }
        }
    }

    fun registration(phoneNum: String, password: String){
        viewModelScope.launch {
            when(val result = authUseCases.createUserUseCase.start(phoneNum,password)){
                is Response.Success -> {
                    _authStateCompose.value = AuthState.UserCreate(result.dataValue)
                }
                is Response.Error -> {
                    setStateByErrorType(result.errorType)
                }
            }
        }
    }

    fun changePass(password: String, phoneNum: String,){
        viewModelScope.launch {
            val jwt = dataStore.readToken().stateIn(this).value

            when(val result = authUseCases.changePasswordUseCase.start(password,phoneNum, jwt ?:"")){
                is Response.Success -> {
                    _authStateCompose.value = AuthState.ChangePass(result.dataValue)
                }
                is Response.Error -> {
                    setStateByErrorType(result.errorType)
                }
            }
        }
    }
    fun logout() {
        viewModelScope.launch {
            dataStore.clearToken()
        }
    }

    private fun setStateByErrorType(errorValue: ServerErrorType) {
        when(errorValue){
            is ServerErrorType.TimeOut ->{
                _authState.value = AuthState.ConnectionError(errorValue.msg)
            }
            else -> {
                _errorState.value = errorValue.msg
            }
        }
    }

    override fun clear() {
        _authState.value = null
        _authStateCompose.value = null
    }
}