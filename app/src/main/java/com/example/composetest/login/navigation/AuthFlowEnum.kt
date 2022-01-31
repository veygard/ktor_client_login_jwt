package com.example.composetest.login.navigation

enum class AuthFlowEnum {
    RecoveryPassword,
    ChangePassword,
    Registration,
    ChangePhoneCurrentNumberOtp, //статус подтверждение Отп для старого номера
    ChangePhoneNewNumberOtp, //статус подтверждение Отп для нового номера
    OtpForProtectedProfileField, //статус подтверждение Отп для редактирования протектед поля профиля
}