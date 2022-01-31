package com.example.composetest.login.presentation.screen.phone_enter

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.example.composetest.login.R
import com.example.composetest.login.presentation.screen.login.phoneNumValidationNextScreen
import com.example.composetest.login.presentation.ui.compose_ui.CommonButton
import com.example.composetest.login.presentation.ui.compose_ui.OneButtonDialog
import com.example.composetest.login.presentation.ui.compose_ui.PhoneNumberInputField
import com.example.composetest.login.presentation.ui.compose_ui.TransparentTopBar
import com.example.composetest.login.presentation.ui.theme.Margin
import com.example.composetest.login.presentation.ui.theme.titleAuthTextStyle
import com.example.composetest.login.util.SpacingVertical
import com.ramcosta.composedestinations.annotation.Destination


enum class PhoneEnterScreenErrorEnum { RegistrationError, ChangePassError, Else }

@Destination
@ExperimentalComposeUiApi //закрытие клавы при наж.энтер
@Composable
fun PhoneEnterScreen(
    topBarTitle: String,
    routeBack: () -> Unit,
) {
    val openErrorDialogState = remember { mutableStateOf(false) }
    val errorTypeState = remember { mutableStateOf(PhoneEnterScreenErrorEnum.Else) }

    Scaffold(
        topBar = {
            TransparentTopBar(topBarTitle) {
                routeBack()
            }
        },
        backgroundColor = MaterialTheme.colors.onBackground,
    ) {
      PhoneEnterScreenContent(openErrorDialogState, errorTypeState)
    }
}

@ExperimentalComposeUiApi
@Composable
fun PhoneEnterScreenContent(
    openErrorDialogState: MutableState<Boolean>,
    errorTypeState: MutableState<PhoneEnterScreenErrorEnum>
) {
    val phoneNumber = remember {
        mutableStateOf("7")
    }

    val scrollState = rememberScrollState()
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
            .verticalScroll(scrollState)
            .padding(start = Margin.horizontalStandard, end = Margin.horizontalStandard)
    ) {
        SpacingVertical(heightDp = 24)
        Image(
            painter = painterResource(id = R.drawable.ic_number_result),
            contentDescription = "Logo",
            alignment = Alignment.Center,
            modifier = Modifier
                .size(32.dp, 32.dp)
        )

        SpacingVertical(heightDp = 36)
        Text(
            text = stringResource(id = R.string.phone_num_text),
            style = titleAuthTextStyle(),
            modifier = Modifier
                .size(311.dp, 20.dp)
        )
        PhoneNumberInputField(
            phoneNumber,
            modifier = Modifier,
            imeAction = ImeAction.Done,
        )
        CommonButton(
            stringResource(R.string.next_screen_button_text),
            modifier = Modifier
                .size(343.dp, 48.dp),
            enabledStatus = phoneNumValidationNextScreen(phoneNumber.value)
        ) {

        }

        if (openErrorDialogState.value) {
            val title = when (errorTypeState.value) {
                PhoneEnterScreenErrorEnum.RegistrationError -> stringResource(id =     R.string.phone_enter_screen_registration_error_title)
                PhoneEnterScreenErrorEnum.ChangePassError -> stringResource(id =     R.string.phone_enter_screen_change_password_error_title)
                PhoneEnterScreenErrorEnum.Else ->  stringResource(id =     R.string.something_wrong_text)
            }
            val text = when (errorTypeState.value) {
                PhoneEnterScreenErrorEnum.RegistrationError -> stringResource(id =     R.string.phone_enter_screen_registration_error_text)
                PhoneEnterScreenErrorEnum.ChangePassError -> stringResource(id =     R.string.phone_enter_screen_change_password_error_text)
                PhoneEnterScreenErrorEnum.Else ->  stringResource(id =     R.string.something_wrong_text)
            }
            OneButtonDialog(title, text, openErrorDialogState)
        }
    }
}
