package com.example.composetest.login.navigation

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.composetest.login.presentation.screen.NavGraphs
import com.example.composetest.login.presentation.screen.destinations.HomeScreenDestination
import com.example.composetest.login.presentation.screen.destinations.LoginScreenDestination
import com.example.composetest.login.presentation.screen.destinations.PhoneEnterScreenDestination
import com.example.composetest.login.presentation.screen.home.HomeScreen
import com.example.composetest.login.presentation.screen.login.LoginScreen
import com.example.composetest.login.presentation.screen.phone_enter.PhoneEnterScreen
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.manualcomposablecalls.composable


@ExperimentalComposeUiApi
@ExperimentalMaterialApi
@Composable
fun AppNavigation(
    navController: NavHostController,
) {
    DestinationsNavHost(
        navGraph = NavGraphs.root,
        navController = navController,
    ) {
        /*Открывает экран который указан как @Destination(start = true), HomeScreen в нашем случае*/
    }
}