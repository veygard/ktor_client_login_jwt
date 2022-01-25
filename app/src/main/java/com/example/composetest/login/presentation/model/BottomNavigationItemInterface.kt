package com.example.composetest.login.presentation.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

//для нижнего меню
interface BottomNavigationItemInterface {
     val iconId: Int
    val contentDescription: Int
     val label: String
    val route:String
}