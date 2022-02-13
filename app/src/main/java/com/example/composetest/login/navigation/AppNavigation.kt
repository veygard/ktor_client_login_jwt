package com.example.composetest.login.navigation

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.navigation.NavHostController
import com.example.composetest.login.presentation.screen.NavGraphs
import com.ramcosta.composedestinations.DestinationsNavHost


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