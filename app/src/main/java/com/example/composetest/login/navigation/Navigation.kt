package com.example.composetest.login.navigation

import androidx.navigation.NavController


fun navigate(navController: NavController, screen:Screens){
    navController.navigate(screen.route)
}