package com.example.composetest.login.navigation

enum class AuthFlowEnum {
    ChangePassword,
    Registration,
}

sealed class Screens(val route: String) {
    object Unauthorized  : Screens("unauthorized_screen")
    object Home : Screens("home_screen")
    object Login : Screens("Login_screen")
    object PhoneEnter : Screens("PhoneEnter_screen")
    object Otp  : Screens("Otp_screen")
    object RegisterFinish  : Screens("RegisterFinish_screen")
}
