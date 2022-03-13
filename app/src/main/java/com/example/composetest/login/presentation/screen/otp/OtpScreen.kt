package com.example.composetest.login.presentation.screen.otp

import android.util.Log
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.composetest.login.R
import com.example.composetest.login.domain.model.auth.CheckOTPResponse
import com.example.composetest.login.navigation.AuthFlowEnum
import com.example.composetest.login.presentation.screen.destinations.LoginScreenDestination
import com.example.composetest.login.presentation.screen.destinations.RegisterFinishScreenDestination
import com.example.composetest.login.presentation.supports.AppNotification.makeNotification
import com.example.composetest.login.presentation.ui.compose_ui.CircleProgressBar
import com.example.composetest.login.presentation.ui.compose_ui.TransparentTopBar
import com.example.composetest.login.presentation.viewmodel.auth.AuthState
import com.example.composetest.login.presentation.viewmodel.auth.AuthViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@ExperimentalComposeUiApi
@ExperimentalMaterialApi
@Destination
@Composable
fun OtpScreen(
    navigator: DestinationsNavigator,
    flow: AuthFlowEnum,
    phoneNumber: String,
    authViewModel: AuthViewModel = hiltViewModel(),
) {
    val context = LocalContext.current

    val otpCodeState: MutableState<String?> = remember { mutableStateOf(null) }
    val scaffoldState = rememberScaffoldState()

    val otpScreenErrorState = OtpScreenErrorState(
        errorState = remember { mutableStateOf(false) },
        errorCode = remember { mutableStateOf(null) }
    )
    val authStateCompose by authViewModel.authStateCompose.collectAsState()


    /*запрашиваем код отп*/
    LaunchedEffect(key1 = phoneNumber, block = {
        authViewModel.loadingShow()
        authViewModel.sendOtp(phoneNumber)
    })

    /*запускам обсервера*/
    observeData(
        authViewModel = authViewModel,
        otpCode = otpCodeState,
        authStateCompose = authStateCompose,
        checkOtpResponseAction = { response ->
            checkOtpResponseAction(
                checkOTPResponse = response,
                navigator = navigator,
                phoneNumber = phoneNumber,
                flow = flow,
                otpScreenErrorState = otpScreenErrorState,
                clear = { authViewModel.clear() }
            )
        },
    )


    /*Нотификация при получении кода от сервера*/
    LaunchedEffect(key1 = otpCodeState.value, block = {
        otpCodeState.value?.let {
            makeNotification(
                context = context,
                title = context.getString(R.string.otp_screen_got_opt_title),
                text = context.getString(R.string.otp_screen_got_opt_text, otpCodeState.value),
            )
        }
    })

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TransparentTopBar(
                title = stringResource(id = R.string.enter_sms_page_top_bar_title)
            ) {
                navigator.navigate(LoginScreenDestination)
            }
        },
        backgroundColor = MaterialTheme.colors.onBackground
    ) {
        CircleProgressBar(authViewModel.loadingState)
        OtpScreenContent(
            phoneNumber = phoneNumber,
            sendOptAgain = {
                authViewModel.sendOtp(phoneNumber)
            },
            otpCodeIsEntered = { otp: String ->
                authViewModel.loadingShow()
                authViewModel.checkOtp(phoneNumber, otp)
            },
            otpScreenErrorState = otpScreenErrorState,
            otpCodeState = otpCodeState
        )
    }
}

@ExperimentalMaterialApi
@ExperimentalComposeUiApi
private fun checkOtpResponseAction(
    checkOTPResponse: CheckOTPResponse,
    navigator: DestinationsNavigator,
    phoneNumber: String,
    flow: AuthFlowEnum,
    otpScreenErrorState: OtpScreenErrorState,
    clear: () -> Unit,
) {
    Log.d("checkOTP", "OtpScreen checkOtpResponseAction, recompose")
    when {
        checkOTPResponse.result == true -> {
            navigator.navigate(RegisterFinishScreenDestination(phoneNumber, flow))
        }
        checkOTPResponse.result == false && checkOTPResponse.expired == true -> {
            Log.d("checkOTP", "OtpScreen checkOtpResponseAction, Expired")
            otpScreenErrorState.errorState.value = true
            otpScreenErrorState.errorCode.value = OtpScreenErrorCode.Expired
            clear()
        }
        checkOTPResponse.result == false && checkOTPResponse.expired == false -> {
            Log.d("checkOTP", "OtpScreen checkOtpResponseAction, Not match")
            otpScreenErrorState.errorState.value = true
            otpScreenErrorState.errorCode.value = OtpScreenErrorCode.NotMatch
            clear()
        }
    }

}

private fun observeData(
    authViewModel: AuthViewModel,
    otpCode: MutableState<String?>,
    checkOtpResponseAction: (checkOTPResponse: CheckOTPResponse) -> Unit,
    authStateCompose: AuthState?
) {
    when (authStateCompose) {
        is AuthState.CheckOtp -> {
            authViewModel.loadingHide()
            Log.d(
                "checkOTP",
                "OtpScreen observeData, CheckOtp result is: ${authStateCompose.checkOTPResponse.result}, message:${authStateCompose.checkOTPResponse.message}"
            )
            checkOtpResponseAction(authStateCompose.checkOTPResponse)
        }
        is AuthState.SendOtp -> {
            authViewModel.loadingHide()
            otpCode.value = authStateCompose.otpResponse.otpCode
        }
    }

    authViewModel.errorState.addObserver { error ->
        if (error != "") {
            Log.d("checkOTP", "OtpScreen observeData, errorState $error")
            authViewModel.loadingHide()
        }
    }
}


data class OtpScreenErrorState(
    val errorState: MutableState<Boolean>,
    var errorCode: MutableState<OtpScreenErrorCode?>
)

enum class OtpScreenErrorCode {
    Expired, NotMatch
}