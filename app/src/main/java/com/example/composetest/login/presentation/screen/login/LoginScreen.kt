package com.example.composetest.login.presentation.screen.login

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.composetest.login.R
import com.example.composetest.login.presentation.screen.destinations.HomeScreenDestination
import com.example.composetest.login.presentation.screen.destinations.PhoneEnterScreenDestination
import com.example.composetest.login.presentation.screen.destinations.TimeOutScreenDestination
import com.example.composetest.login.presentation.ui.compose_ui.CircleProgressBar
import com.example.composetest.login.presentation.ui.compose_ui.TransparentTopBar
import com.example.composetest.login.presentation.viewmodel.auth.AuthState
import com.example.composetest.login.presentation.viewmodel.auth.AuthViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator


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
    LaunchedEffect(key1 = Unit, block = {
        observeData(authViewModel, navigator, showErrorDialogState)
    })


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
        CircleProgressBar(authViewModel.loadingState)
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

@ExperimentalMaterialApi
@ExperimentalComposeUiApi
private fun observeData(
    authViewModel: AuthViewModel,
    navigator: DestinationsNavigator,
    showErrorDialogState: MutableState<Boolean>
) {
    authViewModel.authState.addObserver { result ->
        when (result) {
            is AuthState.Success -> {
                authViewModel.loadingHide()
                navigator.navigate(HomeScreenDestination)
            }
            is AuthState.ConnectionError -> {
                navigator.navigate(TimeOutScreenDestination)
            }
        }
    }
    authViewModel.errorState.addObserver { error ->
        if(error != ""){
            authViewModel.loadingHide()
            showErrorDialogState.value = true
        }
    }
}




