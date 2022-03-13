
package com.example.composetest.login.presentation.screen.register_finish

import android.content.Context
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.graphics.Color
import com.example.composetest.login.R
import com.example.composetest.login.presentation.screen.register_finish.PasswordRequirementIconsStorage.idIconCheckMarkDigit
import com.example.composetest.login.presentation.screen.register_finish.PasswordRequirementIconsStorage.idIconCheckMarkLength
import com.example.composetest.login.presentation.screen.register_finish.PasswordRequirementIconsStorage.idIconCheckMarkOnlyLatin
import com.example.composetest.login.presentation.screen.register_finish.PasswordRequirementIconsStorage.idIconCheckMarkUpperCase
import com.example.composetest.login.util.LocalStuff

private const val idWrong = R.drawable.ic_password_wrong
private const val idCorrect = R.drawable.ic_password_correct
private const val idSteady = R.drawable.ic_password_steady
private const val idSteady_dark = R.drawable.ic_password_steady_dark


fun changeIconsByPasswordRequirement(
    password:String, lostFocus:Boolean = false,
    passwordRequirementState:MutableState<Boolean>
) {
    if(LocalStuff.isDarkTheme.value) idSteady_dark else idSteady
    if (password.isNotEmpty()) {
        passwordRequirementState.value = true
        when{
            password.matches("^[a-zA-Z0-9.]+$".toRegex()) ->
                idIconCheckMarkOnlyLatin.value = idCorrect
            else -> {
                if(lostFocus){idIconCheckMarkOnlyLatin.value = idWrong
                } else {idIconCheckMarkOnlyLatin.value = if(LocalStuff.isDarkTheme.value) idSteady_dark else idSteady
                }
                passwordRequirementState.value = false
            }
        }
        when {
            password.length < 8 -> {
                if(lostFocus){idIconCheckMarkLength.value = idWrong
                } else {idIconCheckMarkLength.value = if(LocalStuff.isDarkTheme.value) idSteady_dark else idSteady
                }
                passwordRequirementState.value = false
            }
            password.length >= 8 -> {
                idIconCheckMarkLength.value = idCorrect
            }
        }
        when{
            password.matches("(.*[A-Z].*)".toRegex()) ->
                idIconCheckMarkUpperCase.value = idCorrect
            else -> {
                if(lostFocus){idIconCheckMarkUpperCase.value= idWrong
                } else {idIconCheckMarkUpperCase.value = if(LocalStuff.isDarkTheme.value) idSteady_dark else idSteady
                }
                passwordRequirementState.value = false
            }
        }
        when{
            password.matches("(.*[0-9].*)".toRegex()) ->
                idIconCheckMarkDigit.value = idCorrect
            else -> {
                if(lostFocus){idIconCheckMarkDigit.value= idWrong
                } else {idIconCheckMarkDigit.value = if(LocalStuff.isDarkTheme.value) idSteady_dark else idSteady
                }
                passwordRequirementState.value = false}
        }
    }else{
        idIconCheckMarkOnlyLatin.value = if(LocalStuff.isDarkTheme.value) idSteady_dark else idSteady
        idIconCheckMarkLength.value= if(LocalStuff.isDarkTheme.value) idSteady_dark else idSteady
        idIconCheckMarkUpperCase.value= if(LocalStuff.isDarkTheme.value) idSteady_dark else idSteady
        idIconCheckMarkDigit.value = if(LocalStuff.isDarkTheme.value) idSteady_dark else idSteady
    }
}


object PasswordRequirementIconsStorage {
    var idIconCheckMarkLength: MutableState<Int> =
        mutableStateOf(R.drawable.ic_password_steady)
    var idIconCheckMarkOnlyLatin: MutableState<Int> =
        mutableStateOf(R.drawable.ic_password_steady)
    var idIconCheckMarkUpperCase: MutableState<Int> =
        mutableStateOf(R.drawable.ic_password_steady)
    var idIconCheckMarkDigit: MutableState<Int> =
        mutableStateOf(R.drawable.ic_password_steady)
}

fun checkNewPasswordStateOnFocusChanged(
    focusState: FocusState,
    password: String,
    passwordRequirementState:MutableState<Boolean>
) {
    //Если фокус есть то неправильные знаки проля - иконка пустой квадрат
    //если фокуса нет - иконка красный крест
    if (!focusState.isFocused) {
        changeIconsByPasswordRequirement(
            password = password, lostFocus = true, passwordRequirementState
        )
    }
    if (focusState.isFocused) {
        changeIconsByPasswordRequirement(
            password = password, lostFocus = false, passwordRequirementState
        )
    }

}

@ExperimentalMaterialApi
@ExperimentalComposeUiApi
fun checkSecondPasswordStateOnFocusChanged(
    focusState: FocusState,
    changingTitleString: MutableState<String>,
    colorTitleState: MutableState<Color>,
    originalTitle: String,
    originalTitleColor: Color,
    errorColor: Color,
    passwordMatchState:MutableState<Boolean>,
    enteredNewPassword:MutableState<String>,
    enteredNewPasswordRepeat:MutableState<String>,
    context:Context
) {
    //сверяем пароли
    passwordMatchState.value = checkPasswordsEquals(enteredNewPassword, enteredNewPasswordRepeat)

    if (!focusState.isFocused) {
        //меняем текст титула и цвет, если ошибка
        if (!passwordMatchState.value) {
            changingTitleString.value = context.getString(R.string.password_not_match_error_text)
            colorTitleState.value = errorColor
        } else {
            changingTitleString.value = originalTitle
            colorTitleState.value = originalTitleColor
        }
    }
    if (focusState.isFocused) {
        changingTitleString.value = originalTitle
        colorTitleState.value = originalTitleColor
    }
}

fun nextScreenValidationCheck(
    passwordRequirementState: MutableState<Boolean>,
    enteredNewPassword: MutableState<String>,
    enteredNewPasswordRepeat: MutableState<String>,
): Boolean {
    return checkPasswordsEquals(enteredNewPassword, enteredNewPasswordRepeat) && passwordRequirementState.value
}

fun checkPasswordsEquals(
    enteredNewPassword: MutableState<String>,
    enteredNewPasswordRepeat: MutableState<String>
): Boolean{
    return enteredNewPassword.value == enteredNewPasswordRepeat.value
}