package com.example.composetest.login.presentation.screen.otp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.composetest.login.R
import com.example.composetest.login.navigation.AuthFlowEnum
import com.example.composetest.login.presentation.screen.destinations.LoginScreenDestination
import com.example.composetest.login.presentation.supports.extensions.formatPhone
import com.example.composetest.login.presentation.supports.text_validation.PhoneMask
import com.example.composetest.login.presentation.ui.compose_ui.OneButtonDialog
import com.example.composetest.login.presentation.ui.compose_ui.TransparentTopBar
import com.example.composetest.login.presentation.ui.theme.Margin
import com.example.composetest.login.presentation.ui.theme.securityMeasuresTitleStyle
import com.example.composetest.login.presentation.viewmodel.auth.AuthViewModel
import com.example.composetest.login.util.SpacingVertical
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

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
    Scaffold(
        topBar = {
            TransparentTopBar(
                title = stringResource(id = R.string.enter_sms_page_top_bar_title)
            ) {
                navigator.navigate(LoginScreenDestination)
            }
        },
        backgroundColor = MaterialTheme.colors.onBackground
    ) {
        OtpScreenContent(
            phoneNumber = phoneNumber,
            sendOptAgain = {

            },
            otpCodeIsEntered = { otp: String, openErrorDialogState -> },
        )
    }

}


