package com.example.composetest.login.presentation.screen.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.composetest.login.R
import com.example.composetest.login.navigation.AuthFlowEnum
import com.example.composetest.login.navigation.Screens
import com.example.composetest.login.navigation.navigate
import com.example.composetest.login.presentation.screen.destinations.HomeScreenDestination
import com.example.composetest.login.presentation.screen.destinations.PhoneEnterScreenDestination
import com.example.composetest.login.presentation.ui.compose_ui.*
import com.example.composetest.login.presentation.ui.theme.Margin
import com.example.composetest.login.presentation.ui.theme.onBackgroundTextStyle
import com.example.composetest.login.presentation.ui.theme.titleAuthTextStyle
import com.example.composetest.login.presentation.viewmodel.auth.AuthState
import com.example.composetest.login.presentation.viewmodel.auth.AuthViewModel
import com.example.composetest.login.util.LocalStuff.getKeyBoardController
import com.example.composetest.login.util.LocalStuff.getLocalFocusManager
import com.example.composetest.login.util.SpacingVertical
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.launch
import kotlin.math.roundToInt


@Destination
@ExperimentalMaterialApi
@ExperimentalComposeUiApi
@Composable
fun LoginScreen(
    navigator: DestinationsNavigator,
    authViewModel: AuthViewModel = hiltViewModel(),
) {
    val showErrorDialogState = remember {
        mutableStateOf(false)
    }

    authViewModel.authState.addObserver { result ->
        when (result) {
            is AuthState.Success -> {
                authViewModel.loadingHide()
               navigator.navigate(HomeScreenDestination)
            }
        }
    }
    authViewModel.errorState.addObserver { error ->
        if(error != ""){
            authViewModel.loadingHide()
            showErrorDialogState.value = true
        }
    }

    Scaffold(
        Modifier
            .fillMaxSize(),
        topBar = {
            TransparentTopBar(stringResource(id = R.string.login_screen_title)) {
                navigator.navigate(HomeScreenDestination)
            }
        },
        backgroundColor = MaterialTheme.colors.background,
    ) {
        LoginScreenContent(
            onEnterClick = { phone, password ->
                authViewModel.loadingShow()
                authViewModel.login(phone = phone, password = password)
            },
            routeToPhoneEnter = {flow->
                navigator.navigate(PhoneEnterScreenDestination(flow))
            },
            showErrorDialogState
        )
    }
}




