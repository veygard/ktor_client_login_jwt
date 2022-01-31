package com.example.composetest.login.util

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController

object LocalStuff {
    @ExperimentalComposeUiApi
    private val keyboardController: LocalSoftwareKeyboardController =
        LocalSoftwareKeyboardController
    private val localFocusManager: ProvidableCompositionLocal<FocusManager> = LocalFocusManager

    @ExperimentalComposeUiApi
    @Composable
    fun getKeyBoardController(): LocalSoftwareKeyboardController {
        return keyboardController
    }

    @Composable
    fun getLocalFocusManager(): ProvidableCompositionLocal<FocusManager> {
        return localFocusManager
    }

    @Composable
    fun setTheme(){
        isDarkTheme.value =  isSystemInDarkTheme()
    }
    var isDarkTheme = mutableStateOf(false)

    fun toggleTheme() {
        isDarkTheme.value = !isDarkTheme.value
    }
}