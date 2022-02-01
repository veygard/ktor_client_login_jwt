package com.example.composetest.login.presentation.screen.register_finish

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.composetest.login.R
import com.example.composetest.login.navigation.AuthFlowEnum
import com.example.composetest.login.presentation.screen.destinations.LoginScreenDestination
import com.example.composetest.login.presentation.ui.compose_ui.TransparentTopBar
import com.example.composetest.login.presentation.viewmodel.auth.AuthViewModel
import com.example.composetest.login.util.LocalStuff.getLocalFocusManager
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator



@ExperimentalComposeUiApi
@ExperimentalMaterialApi
@Composable
@Destination
fun RegisterFinishScreen(
    navigator: DestinationsNavigator,
    flow: AuthFlowEnum,
    authViewModel: AuthViewModel = hiltViewModel(),
) {
    val focusManager = getLocalFocusManager().current
    val coroutineScope = rememberCoroutineScope()
    val passwordRequirementState = remember { mutableStateOf(false) }
    val passwordMatchState = remember { mutableStateOf(true) }

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
        backgroundColor = MaterialTheme.colors.onBackground
    ) {
        Text(text = "Register Finish")
    }
}


