package com.example.composetest.login.presentation.screen.home

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.composetest.login.R
import com.example.composetest.login.domain.model.User
import com.example.composetest.login.presentation.screen.destinations.LoginScreenDestination
import com.example.composetest.login.presentation.screen.destinations.TimeOutScreenDestination
import com.example.composetest.login.presentation.screen.timeout.TimeOutScreen
import com.example.composetest.login.presentation.ui.compose_ui.*
import com.example.composetest.login.presentation.viewmodel.auth.AuthState
import com.example.composetest.login.presentation.viewmodel.auth.AuthViewModel
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
    val isAuthorized: MutableState<Boolean?> = remember { mutableStateOf(null) }
    val user: MutableState<User?> = remember { mutableStateOf(null) }
    /*Проверяем авторизацию юзера*/
    LaunchedEffect(key1 = true, block = {
        authViewModel.loadingShow()
        authViewModel.getUser()

        authViewModel.authState.addObserver { result ->
            when (result) {
                is AuthState.GotUser -> {
                    isAuthorized.value = true
                    user.value = result.user
                    authViewModel.loadingHide()
                }
                is AuthState.ConnectionError -> {
                    isAuthorized.value = null
                }
            }
        }
    })

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
                null -> navigator.navigate(TimeOutScreenDestination)
            }
        }
    )
}


