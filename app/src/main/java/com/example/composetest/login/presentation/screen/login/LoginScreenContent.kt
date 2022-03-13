package com.example.composetest.login.presentation.screen.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.composetest.login.R
import com.example.composetest.login.navigation.AuthFlowEnum
import com.example.composetest.login.presentation.ui.compose_ui.*
import com.example.composetest.login.presentation.ui.theme.Margin
import com.example.composetest.login.presentation.ui.theme.onBackgroundTextStyle
import com.example.composetest.login.presentation.ui.theme.titleAuthTextStyle
import com.example.composetest.login.util.LocalStuff
import com.example.composetest.login.util.SpacingVertical
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@ExperimentalComposeUiApi
@ExperimentalMaterialApi
@Composable
internal fun LoginScreenContent(
    onEnterClick: (phone: String, password: String) -> Unit,
    routeToPhoneEnter: (flow: AuthFlowEnum) -> Unit,
    showErrorDialogState: MutableState<Boolean>,
) {

    val focusManager = LocalStuff.getLocalFocusManager().current

    val keyboard = LocalStuff.getKeyBoardController().current

    val scrollState = rememberScrollState()
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
            .verticalScroll(scrollState)
            .padding(start = Margin.horizontalStandard, end = Margin.horizontalStandard)
    ) {
        val phoneNumber = remember {
            mutableStateOf("7")
        }
        val password = remember {
            mutableStateOf("")
        }

        SpacingVertical(heightDp = 44)
        Image(
            painter = painterResource(id = R.drawable.ic_auth_logo),
            contentDescription = "Logo",
            alignment = Alignment.Center,
            modifier = Modifier
                .size(164.dp, 164.dp)
        )


        var passwordFieldCoordinates by remember { mutableStateOf(0f) }
        val coroutineScope = rememberCoroutineScope()

        SpacingVertical(heightDp = 24)
        Text(
            text = stringResource(id = R.string.phone_num_text),
            style = titleAuthTextStyle(),
            modifier = Modifier.fillMaxWidth()
        )

        SpacingVertical(heightDp = 8)
        PhoneNumberInputField(
            phoneNumState = phoneNumber,
            modifier = Modifier,
            imeAction = ImeAction.Next,
            moreOnNextAction = {
                focusManager.moveFocus(FocusDirection.Down)
            },
            moreOnValueChanged = { value ->
                if (value.length == 11) focusManager.moveFocus(FocusDirection.Down)
            }
        )

        SpacingVertical(heightDp = 16)
        Text(
            text = stringResource(id = R.string.write_password_text),
            style = titleAuthTextStyle(),
            modifier = Modifier.fillMaxWidth()
        )
        SpacingVertical(heightDp = 8)
        PasswordInputField(
            password,
            modifier = Modifier
                .onGloballyPositioned { coordinates ->
                    passwordFieldCoordinates = coordinates.positionInParent().y
                }
                //при фокусе скролим экран до поля, чтобы клава не перекрывала ввод
                .fillMaxWidth()
                .onFocusChanged {
                    if (it.isFocused) {
                        coroutineScope.launch {
                            scrollState.scrollTo(passwordFieldCoordinates.roundToInt())
                        }
                    }
                },
            imeAction = ImeAction.Done,
        )

        SpacingVertical(heightDp = 16)
        TransparentBackgroundButton(
            label = stringResource(id = R.string.forgot_password_button_text),
            textColor = MaterialTheme.colors.secondary,
            modifier = Modifier
        ) {
            routeToPhoneEnter(AuthFlowEnum.ChangePassword)
        }

        //login button
        SpacingVertical(heightDp = 16)
        CommonButton(
            label = stringResource(R.string.login_button_text),
            enabledStatus = authorizationFieldsFill(phoneNumber.value, password.value),
            modifier = Modifier,
        ) {
            coroutineScope.launch {
                keyboard?.hide()
            }
            onEnterClick(
                phoneNumber.value, password.value
            )
        }

        SpacingVertical(heightDp = 18)
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(id = R.string.first_time_here_text),
                style = onBackgroundTextStyle(),
                textAlign= TextAlign.End,
            )
            TransparentBackgroundButton(
                label = stringResource(id = R.string.sign_up_button_text),
                textColor = MaterialTheme.colors.primary,
            ) {
                routeToPhoneEnter(AuthFlowEnum.Registration)
            }
        }

        //Диалог, если авторизация не прошла
        if (showErrorDialogState.value) {
            val title =
                stringResource(id = R.string.authorization_error_dialog_title_text)
            val text = stringResource(id = R.string.authorization_error_dialog_text)
            OneButtonDialog(title, text, showErrorDialogState)
        }
    }
}

fun phoneNumValidationNextScreen(phoneNumber: String): Boolean {
    return phoneNumber.length == 11
}

fun authorizationFieldsFill(phoneNumber: String, password: String): Boolean {
    return phoneNumValidationNextScreen(phoneNumber) && passwordFieldNotEmpty(password)
}

fun passwordFieldNotEmpty(password: String): Boolean {
    return password.isNotEmpty()
}
