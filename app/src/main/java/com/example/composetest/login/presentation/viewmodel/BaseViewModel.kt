package com.example.composetest.login.presentation.viewmodel

import dev.icerock.moko.mvvm.livedata.LiveData
import dev.icerock.moko.mvvm.livedata.MutableLiveData
import dev.icerock.moko.mvvm.viewmodel.ViewModel

abstract class BaseViewModel : ViewModel() {

    protected val _loadingState = MutableLiveData<LoadingState>(LoadingState.Hide)
    val loadingState: LiveData<LoadingState> = _loadingState

    protected val _errorState = MutableLiveData<String?>("")
    val errorState: LiveData<String?> = _errorState

    fun loadingShow(){
        _loadingState.value = LoadingState.Show
    }

    fun loadingHide(){
        _loadingState.value = LoadingState.Hide
    }
    abstract fun clear()

    private val _failureState = MutableLiveData<Failure>(Failure.Default)

    val failureState: LiveData<Failure>
        get() = _failureState



    private val handleLoadingState: (LoadingState) -> Unit = { state ->
        when (state) {
            LoadingState.Show -> {

            }
            LoadingState.Hide -> {
            }
        }
        _loadingState.value = state
    }

    private val handleFailure: (Failure) -> Unit = {
        handleLoadingState(LoadingState.Hide)
        _failureState.value = it
    }

}


sealed class LoadingState {
    object Show : LoadingState()
    object Hide : LoadingState()
}

sealed class Failure : RuntimeException() {
    open var errorMessage: String? = null


    data class BaseFailure(override val message: String,val system: String,val status: Int,val developerMessage: String) : Failure()
    fun isBaseFailure() = this is BaseFailure

    data class UnknownException(override var errorMessage: String?) : Failure()
    fun isUnknown() = this is UnknownException

    object  NotFound : Failure()
    fun isNotFound() = this is NotFound

    object Default : Failure()
    data class ServerError(override var errorMessage: String?) : Failure()
    data class NetworkError(override var errorMessage: String?) : Failure()

    data class DatabaseError(override var errorMessage: String?) : Failure()


    abstract class FeatureFailure : Failure()

    object UseCaseError : FeatureFailure()

}