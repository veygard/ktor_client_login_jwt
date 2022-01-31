package com.example.composetest.login.presentation.screen.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
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
import com.example.composetest.login.presentation.ui.compose_ui.CircleProgressBar
import com.example.composetest.login.presentation.ui.compose_ui.CommonButton
import com.example.composetest.login.presentation.ui.theme.H_L3
import com.example.composetest.login.presentation.ui.theme.Margin
import com.example.composetest.login.util.SpacingVertical
import com.ramcosta.composedestinations.annotation.Destination

@Destination
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
        Image(
            painter = painterResource(id = R.drawable.ic_home_empty),
            contentDescription = "home",
            alignment = Alignment.Center,
            modifier = Modifier
                .size(164.dp, 164.dp)
        )
        SpacingVertical(heightDp = 12)
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            Text(
                text = stringResource(id = R.string.home_screen_not_authorized_text),
                style = H_L3,
                color = MaterialTheme.colors.onBackground,
                modifier = Modifier.fillMaxWidth(0.7f)
            )

        }
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
        Image(
            painter = painterResource(id = R.drawable.ic_home_full),
            contentDescription = "home",
            alignment = Alignment.Center,
            modifier = Modifier
                .size(164.dp, 164.dp)
        )
        SpacingVertical(heightDp = 12)
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
