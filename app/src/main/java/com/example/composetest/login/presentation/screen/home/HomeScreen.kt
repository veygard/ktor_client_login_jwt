package com.example.composetest.login.presentation.screen.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.composetest.login.presentation.ui.compose_ui.ScaffoldElement
import com.example.composetest.login.presentation.ui.compose_ui.TransparentTopBar
import com.example.composetest.login.presentation.viewmodel.auth.AuthState
import com.example.composetest.login.presentation.viewmodel.auth.AuthViewModel
import com.example.composetest.login.R
import com.example.composetest.login.domain.model.User
import com.example.composetest.login.navigation.Screens
import com.example.composetest.login.navigation.navigate
import com.example.composetest.login.presentation.screen.destinations.LoginScreenDestination
import com.example.composetest.login.presentation.ui.compose_ui.CircleProgressBar
import com.example.composetest.login.presentation.ui.compose_ui.CommonButton
import com.example.composetest.login.presentation.ui.theme.H_L3
import com.example.composetest.login.presentation.ui.theme.Margin
import com.example.composetest.login.util.SpacingVertical
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@ExperimentalComposeUiApi
@ExperimentalMaterialApi
@Destination(start = true)
@Composable
fun HomeScreen(
    navigator: DestinationsNavigator,
    authViewModel: AuthViewModel = hiltViewModel(),
) {

    /*Проверяем авторизацию юзера*/
    LaunchedEffect(key1 = true, block = {
        authViewModel.loadingShow()
        authViewModel.getUser()
    })

    val isAuthorized: MutableState<Boolean?> = remember { mutableStateOf(null) }
    val user: MutableState<User?> = remember { mutableStateOf(null) }

    authViewModel.authState.addObserver { result ->
        when (result) {
            is AuthState.GotUser -> {
                isAuthorized.value = true
                user.value = result.user
                authViewModel.loadingHide()
            }
            is AuthState.NoUser -> {
                isAuthorized.value = false
                authViewModel.loadingHide()
            }
        }
    }
    ScaffoldElement(
        modifier = Modifier,
        topBarShow = true,
        topBarContent = {
            TransparentTopBar(
                stringResource(id = R.string.home_screen_title),
                backIconsShow = false
            )
        },
        bottomBarShow = false,
        mainContent = {
            CircleProgressBar(authViewModel.loadingState)
            /*Когда прошли авторизацию - показываем контент в зависимости от статуса логина*/
            isAuthorized.value?.let {
                when (isAuthorized.value) {
                    true -> {
                        HomeScreenAuthorizedContent(
                            userId = user.value?.userId,
                            logoutClick = {
                                isAuthorized.value = false
                                authViewModel.logout()
                            })
                    }
                    false -> {
                        HomeScreenNotAuthorizedContent(
                            loginClick = {
                                navigator.navigate(LoginScreenDestination())
                            }
                        )
                    }
                }
            }
        }
    )
}


