package com.example.composetest.login.presentation.screen.phone_enter

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.composetest.login.R
import com.example.composetest.login.navigation.AuthFlowEnum
import com.example.composetest.login.presentation.screen.destinations.LoginScreenDestination
import com.example.composetest.login.presentation.screen.login.phoneNumValidationNextScreen
import com.example.composetest.login.presentation.ui.compose_ui.CommonButton
import com.example.composetest.login.presentation.ui.compose_ui.OneButtonDialog
import com.example.composetest.login.presentation.ui.compose_ui.PhoneNumberInputField
import com.example.composetest.login.presentation.ui.compose_ui.TransparentTopBar
import com.example.composetest.login.presentation.ui.theme.Margin
import com.example.composetest.login.presentation.ui.theme.titleAuthTextStyle
import com.example.composetest.login.presentation.viewmodel.auth.AuthViewModel
import com.example.composetest.login.util.SpacingVertical
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator


enum class PhoneEnterScreenErrorEnum { RegistrationError, ChangePassError, Else }

@ExperimentalMaterialApi
@Destination
@ExperimentalComposeUiApi //закрытие клавы при наж.энтер
@Composable
fun PhoneEnterScreen(
    navigator: DestinationsNavigator,
    flow: AuthFlowEnum,
    authViewModel: AuthViewModel = hiltViewModel(),
) {
    val openErrorDialogState = remember { mutableStateOf(false) }
    val errorTypeState = remember { mutableStateOf(PhoneEnterScreenErrorEnum.Else) }

    Scaffold(
        topBar = {
            TransparentTopBar(
                when (flow) {
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
            errorTypeState,
            nextButtonClickAction={

            }
        )
    }
}

