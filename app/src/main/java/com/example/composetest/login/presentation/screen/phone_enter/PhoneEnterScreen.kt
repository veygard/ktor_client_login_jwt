package com.example.composetest.login.presentation.screen.phone_enter

import android.util.Log
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.composetest.login.R
import com.example.composetest.login.navigation.AuthFlowEnum
import com.example.composetest.login.presentation.screen.destinations.LoginScreenDestination
import com.example.composetest.login.presentation.screen.destinations.OtpScreenDestination
import com.example.composetest.login.presentation.screen.destinations.RegisterFinishScreenDestination
import com.example.composetest.login.presentation.ui.compose_ui.TransparentTopBar
import com.example.composetest.login.presentation.viewmodel.auth.AuthState
import com.example.composetest.login.presentation.viewmodel.auth.AuthViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator


enum class PhoneEnterScreenErrorEnum { RegistrationError, ChangePassError, Else }

@ExperimentalMaterialApi
@Destination
@ExperimentalComposeUiApi //закрытие клавы при наж.энтер
@Composable
fun PhoneEnterScreen(
    navigator: DestinationsNavigator,
    authFlow: AuthFlowEnum,
    authViewModel: AuthViewModel = hiltViewModel(),
) {
    val openErrorDialogState = remember { mutableStateOf(false) }
    val errorTypeState = remember { mutableStateOf(PhoneEnterScreenErrorEnum.Else) }
    val phoneNumber = remember {
        mutableStateOf("7")
    }

    observeData(
        authViewModel = authViewModel,
        chekUserResultAction = { isFound ->
            chekUserResultAction(
                isFound = isFound,
                authFlow,
                navigator,
                errorTypeState,
                openErrorDialogState,
                phoneNumber
            )
        },
        errorAction = {
            authViewModel.loadingHide()
            errorTypeState.value = PhoneEnterScreenErrorEnum.Else
            openErrorDialogState.value = true
        }

    )

    Scaffold(
        topBar = {
            TransparentTopBar(
                when (authFlow) {
                    AuthFlowEnum.ChangePassword -> stringResource(id = R.string.restore_pass_phone_form_TopBar_title)
                    AuthFlowEnum.Registration -> stringResource(id = R.string.sign_up_phone_form_TopBar_title)
                }
            ) {
                navigator.navigate(LoginScreenDestination)
            }
        },
        backgroundColor = MaterialTheme.colors.onBackground,
    ) {
        PhoneEnterScreenContent(
            openErrorDialogState,
            phoneNumber,
            errorTypeState,
            nextButtonClickAction = {
                authViewModel.checkUser(phoneNumber.value)
            }
        )
    }
}

@ExperimentalMaterialApi
@ExperimentalComposeUiApi
private fun chekUserResultAction(
    isFound: Boolean?,
    flow: AuthFlowEnum,
    navigator: DestinationsNavigator,
    errorTypeState: MutableState<PhoneEnterScreenErrorEnum>,
    openErrorDialogState: MutableState<Boolean>,
    phoneNumber: MutableState<String>,
) {
    when {
        isFound == true && flow == AuthFlowEnum.ChangePassword -> {
            navigator.navigate(OtpScreenDestination(flow, phoneNumber.value))
        }
        isFound == false && flow == AuthFlowEnum.ChangePassword -> {
            errorTypeState.value = PhoneEnterScreenErrorEnum.ChangePassError
            openErrorDialogState.value = true
        }

        isFound == true && flow == AuthFlowEnum.Registration -> {
            errorTypeState.value = PhoneEnterScreenErrorEnum.RegistrationError
            openErrorDialogState.value = true
        }

        isFound == false && flow == AuthFlowEnum.Registration -> {
            navigator.navigate(OtpScreenDestination(flow, phoneNumber.value))
        }
    }

}

@ExperimentalMaterialApi
@ExperimentalComposeUiApi
private fun observeData(
    authViewModel: AuthViewModel,
    chekUserResultAction: (isFound: Boolean?) -> Unit,
    errorAction: () -> Unit,
) {
    authViewModel.authState.addObserver { result ->
        when (result) {
            is AuthState.CheckUser -> {
                Log.d("checkUser", "observeData, CheckUser result is: ${result.isFound}")
                chekUserResultAction(result.isFound)
            }
        }
    }
    authViewModel.errorState.addObserver { error ->
        if (error != "") {
            Log.d("checkUser", "observeData, error")
            errorAction()
        }
    }
}

