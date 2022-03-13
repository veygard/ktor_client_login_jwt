package com.example.composetest.login.presentation.ui.compose_ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.composetest.login.R
import com.example.composetest.login.presentation.screen.register_finish.changeIconsByPasswordRequirement
import com.example.composetest.login.presentation.screen.register_finish.checkNewPasswordStateOnFocusChanged
import com.example.composetest.login.presentation.screen.register_finish.checkSecondPasswordStateOnFocusChanged
import com.example.composetest.login.presentation.ui.theme.Paragraph_16
import com.example.composetest.login.presentation.ui.theme.inputFieldsShapes
import com.example.composetest.login.util.LocalStuff

@ExperimentalMaterialApi
@ExperimentalComposeUiApi
@Composable
fun PasswordInputField(
    passwordValue: MutableState<String>,
    modifier: Modifier = Modifier,
    originalTitle: String = "",
    colorTitleState: MutableState<Color> = mutableStateOf(Color.Red),
    originalTitleColor: Color = Color.Gray,
    placeholderText: @Composable () -> Unit = {
        Text(
            "*********",
            color = MaterialTheme.colors.secondary
        )
    },
    imeAction: ImeAction = ImeAction.Done,
    keyboardType: KeyboardType = KeyboardType.Password,
    moreOnDoneAction: () -> Unit = {},
    moreOnNextAction: () -> Unit = {},
    leadingIcon: @Composable (() -> Unit)? = {
        Image(
            painter = painterResource(id = R.drawable.ic_lock_password), "",
            modifier = Modifier.padding(start = 4.dp, top = 14.67.dp, bottom = 15.33.dp)
        )
    },
    //валидация ввода, используется при ввода СVC карты
    textValidation: (String) -> Boolean = { true },
    //Если в качестве ошибки нужно менять титул/цвет у поля
    changingTitleString: MutableState<String> = mutableStateOf(""),
    //блок для проверки пароля на совпадение для двух полей-паролей
    //для проверки нового пароля на валидацию
    setUpNewPassword: Boolean = false,
    //если нужно проверить пароль на совпадение
    checkSecondPassword: Boolean = false,
    passwordRequirementState: MutableState<Boolean> = mutableStateOf(false),
    passwordMatchState: MutableState<Boolean> = mutableStateOf(true),
    passwordRepeatValue: MutableState<String> = mutableStateOf(""),
    moreOnValueChange:((value:String)->Unit)? = null
) {
    val context = LocalContext.current
    var passwordVisibility by remember { mutableStateOf(false) }

    val image = if (passwordVisibility)
        painterResource(id = R.drawable.ic_eye_open)
    else
        painterResource(id = R.drawable.ic_eye)

    val focusManager = LocalStuff.getLocalFocusManager().current
    val errorColor = MaterialTheme.colors.error

    TextField(
        value = passwordValue.value,
        onValueChange = {
            if (textValidation(it)) {
                passwordValue.value = it
                //меняем иконки требований к паролю, если вводится новый пароль
                if (setUpNewPassword) {
                    changeIconsByPasswordRequirement(
                        password = passwordValue.value,
                        passwordRequirementState = passwordRequirementState
                    )
                }
                moreOnValueChange?.invoke(it)
            }
        },
        //для вывода ошибки валидации
//        isError = isError,

        modifier = modifier
            .onFocusChanged {
                //если пропал фокус поля
                //при новом пароле сверяем пароль еще раз (и помечаем красным невыполненные требования)
                //если поле повторного ввода - смотрим совпадает ли пароль с первым
                if (setUpNewPassword) checkNewPasswordStateOnFocusChanged(
                    it,
                    passwordValue.value,
                    passwordRequirementState
                )
                if (checkSecondPassword) checkSecondPasswordStateOnFocusChanged(
                    it,
                    changingTitleString,
                    colorTitleState,
                    originalTitle,
                    originalTitleColor,
                    errorColor,
                    passwordMatchState,
                    passwordValue,
                    passwordRepeatValue,
                    context
                )

            }
            .shadow(4.dp, inputFieldsShapes.medium)
            .heightIn(54.dp),
        singleLine = true,
        placeholder = placeholderText,
        visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = keyboardType,
            imeAction = imeAction
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                focusManager.clearFocus()
                moreOnDoneAction()
            },
            onNext = {
                moreOnNextAction()
            }
        ),
        textStyle = Paragraph_16,
        shape = inputFieldsShapes.medium,
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = MaterialTheme.colors.surface,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            cursorColor = MaterialTheme.colors.secondary,
            textColor = MaterialTheme.colors.onSurface
        ),
        leadingIcon = leadingIcon,

        trailingIcon = {
            IconButton(onClick = {
                passwordVisibility = !passwordVisibility
            }) {
                Icon(
                    painter = image,
                    contentDescription = stringResource(R.string.contentDescription_password),
                    tint = MaterialTheme.colors.secondary,
                    modifier = Modifier
                )
            }
        },
    )

}
