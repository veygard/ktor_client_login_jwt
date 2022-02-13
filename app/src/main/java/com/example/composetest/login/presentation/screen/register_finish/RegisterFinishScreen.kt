package com.example.composetest.login.presentation.screen.register_finish

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.composetest.login.R
import com.example.composetest.login.navigation.AuthFlowEnum
import com.example.composetest.login.presentation.screen.destinations.LoginScreenDestination
import com.example.composetest.login.presentation.ui.compose_ui.CircleProgressBar
import com.example.composetest.login.presentation.ui.compose_ui.CommonButton
import com.example.composetest.login.presentation.ui.compose_ui.PasswordInputField
import com.example.composetest.login.presentation.ui.compose_ui.TransparentTopBar
import com.example.composetest.login.presentation.ui.theme.*
import com.example.composetest.login.presentation.viewmodel.auth.AuthViewModel
import com.example.composetest.login.util.LocalStuff.getLocalFocusManager
import com.example.composetest.login.util.SpacingVertical
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.launch
import kotlin.math.roundToInt


@ExperimentalComposeUiApi
@ExperimentalMaterialApi
@Composable
@Destination
internal fun RegisterFinishScreen(
    navigator: DestinationsNavigator,
    phoneNumber: String,
    flow: AuthFlowEnum,
    authViewModel: AuthViewModel = hiltViewModel(),
) {
    val authStateCompose by authViewModel.authStateCompose.collectAsState()

    Scaffold(
        topBar = {
            TransparentTopBar(
                when (flow) {
                    AuthFlowEnum.ChangePassword -> stringResource(id = R.string.restore_pass_phone_form_TopBar_title)
                    AuthFlowEnum.Registration -> stringResource(id = R.string.sign_up_phone_form_TopBar_title)
                }
            ) {
                navigator.navigate(LoginScreenDestination)
            }
        },
        backgroundColor = MaterialTheme.colors.onBackground
    ) {
        CircleProgressBar(authViewModel.loadingState)
        RegisterFinishScreenContent(
            saveButtonAction = {

            }
        )
    }
}

@ExperimentalMaterialApi
@ExperimentalComposeUiApi
@Composable
fun RegisterFinishScreenContent(
    saveButtonAction: (pass: String) -> Unit
) {

    val focusManager = getLocalFocusManager().current
    val coroutineScope = rememberCoroutineScope()
    val passwordRequirementState = remember { mutableStateOf(false) }
    val passwordMatchState = remember { mutableStateOf(true) }

    val scrollState = rememberScrollState()
    Column(
        Modifier
            .background(MaterialTheme.colors.background)
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(start = Margin.horizontalStandard, end = Margin.horizontalStandard)
    ) {
        SpacingVertical(heightDp = 24)
        Text(
            text = stringResource(id = R.string.security_measures_title),
            modifier = Modifier
                .padding(start = Margin.horizontalStandard, end = Margin.horizontalStandard)
                .fillMaxWidth(),
            style = securityMeasuresTitleStyle()
        )
        SpacingVertical(heightDp = 12)
        Column(
            modifier = Modifier
                .padding(start = Margin.horizontalStandard, end = Margin.horizontalStandard)
        ) {
            //блок требований к паролю
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = PasswordRequirementIconsStorage.idIconCheckMarkOnlyLatin.value),
                    contentDescription = stringResource(id = R.string.contentDescription_password_page_check_mark),
                )
                Text(
                    text = stringResource(id = R.string.password_requirements_text_1),
                    style = passwordRequirementsTextStyle(),
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(top = 8.dp)
            ) {
                Image(
                    painter = painterResource(id = PasswordRequirementIconsStorage.idIconCheckMarkLength.value),
                    contentDescription = stringResource(id = R.string.contentDescription_password_page_check_mark),
                )
                Text(
                    text = stringResource(id = R.string.password_requirements_text_2),
                    style = passwordRequirementsTextStyle(),
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(top = 8.dp)
            ) {
                Image(
                    painter = painterResource(id = PasswordRequirementIconsStorage.idIconCheckMarkUpperCase.value),
                    contentDescription = stringResource(id = R.string.contentDescription_password_page_check_mark),
                )
                Text(
                    text = stringResource(id = R.string.password_requirements_text_3),
                    style = passwordRequirementsTextStyle(),
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(top = 8.dp)
            ) {
                Image(
                    painter = painterResource(id = PasswordRequirementIconsStorage.idIconCheckMarkDigit.value),
                    contentDescription = stringResource(id = R.string.contentDescription_password_page_check_mark),
                )
                Text(
                    text = stringResource(id = R.string.password_requirements_text_4),
                    style = passwordRequirementsTextStyle(),
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
        }
        SpacingVertical(heightDp = 32)
        //титул первого пароля и его поля
        Text(
            text = stringResource(id = R.string.write_password_text),
            style = titleAuthTextStyle(),
            modifier = Modifier
                .padding(start = Margin.horizontalStandard, end = Margin.horizontalStandard)
                .fillMaxWidth(),
        )

        val enteredNewPassword = remember { mutableStateOf("") }
        SpacingVertical(heightDp = 8)
        PasswordInputField(
            modifier = Modifier.fillMaxWidth(),
            passwordValue = enteredNewPassword,
            setUpNewPassword = true,
            imeAction = ImeAction.Next,
            moreOnNextAction = {
                focusManager.moveFocus(FocusDirection.Down)
            },
            passwordRequirementState = passwordRequirementState,
            passwordMatchState = passwordMatchState
        )
        SpacingVertical(heightDp = 16)

        //Титул поля повторного ввода пароля
        val enterAgainPassTitle =
            stringResource(id = R.string.write_again_password_text)
        val enterAgainPassTitleState = remember { mutableStateOf(enterAgainPassTitle) }
        val colorState = remember { mutableStateOf(Grey_50) }
        Text(
            text = enterAgainPassTitleState.value,
            style = titleAuthTextStyle(),
            color = colorState.value,
            modifier = Modifier
                .padding(start = Margin.horizontalStandard, end = Margin.horizontalStandard)
                .fillMaxWidth()
        )

        //Поле повторного ввода пароля
        var positionInParent = 0f
        val enteredNewPasswordRepeat = remember { mutableStateOf("") }
        SpacingVertical(heightDp = 8)
        PasswordInputField(
            passwordValue = enteredNewPasswordRepeat,
            checkSecondPassword = true,
            changingTitleString = enterAgainPassTitleState,
            originalTitle = enterAgainPassTitle,
            colorTitleState = colorState,
            originalTitleColor = MaterialTheme.colors.secondary,
            modifier = Modifier
                .fillMaxWidth()
                .onGloballyPositioned { coordinates ->
                    positionInParent = coordinates.positionInParent().y
                }
                .onFocusChanged {
                    if (it.isFocused) {
                        coroutineScope.launch {
                            scrollState.scrollTo(positionInParent.roundToInt())
                        }
                    }
                },
            imeAction = ImeAction.Done,
        )

        SpacingVertical(heightDp = 24)
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            CommonButton(
                stringResource(R.string.next_title),
                enabledStatus = nextScreenValidationCheck(
                    passwordRequirementState,
                    enteredNewPassword,
                    enteredNewPasswordRepeat
                )
            ) {
                focusManager.clearFocus()
                saveButtonAction(enteredNewPassword.value)
            }
        }
    }
}
