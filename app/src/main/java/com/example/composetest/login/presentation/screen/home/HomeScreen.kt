package com.example.composetest.login.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
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
import com.example.composetest.login.presentation.ui.compose_ui.CircleProgressBar
import com.example.composetest.login.presentation.ui.compose_ui.CommonButton
import com.example.composetest.login.presentation.ui.theme.H_L3
import com.example.composetest.login.presentation.ui.theme.Margin
import com.example.composetest.login.util.SpacingVertical


@Composable
fun HomeScreen(
    navController: NavController,
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
                                navigate(navController, Screens.Login)
                            }
                        )
                    }
                }
            }
        }
    )
}

@Composable
fun HomeScreenNotAuthorizedContent(
    loginClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = Margin.horizontalStandard, end = Margin.horizontalStandard),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = R.string.home_screen_not_authorized_text),
            style = H_L3,
            color = MaterialTheme.colors.onBackground
        )
        SpacingVertical(heightDp = 24)
        CommonButton(
            label = stringResource(id = R.string.home_screen_not_authorized_button),
            click = {
                loginClick()
            }
        )
    }
}

@Composable
fun HomeScreenAuthorizedContent(
    userId: Int?,
    logoutClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = Margin.horizontalStandard, end = Margin.horizontalStandard),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(
                id = R.string.home_screen_welcome_text,
                userId ?: 0
            ),
            style = H_L3,
            color = MaterialTheme.colors.onBackground
        )
        SpacingVertical(heightDp = 24)
        CommonButton(
            label = stringResource(id = R.string.home_screen_logout_button),
            click = {
                logoutClick()
            }
        )
    }
}
