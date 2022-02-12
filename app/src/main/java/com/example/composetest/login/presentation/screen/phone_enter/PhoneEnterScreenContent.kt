package com.example.composetest.login.presentation.screen.phone_enter

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
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
import com.example.composetest.login.presentation.ui.theme.Margin
import com.example.composetest.login.presentation.ui.theme.titleAuthTextStyle
import com.example.composetest.login.util.SpacingVertical

@ExperimentalComposeUiApi
@Composable
internal fun PhoneEnterScreenContent(
    openErrorDialogState: MutableState<Boolean>,
    phoneNumber: MutableState<String>,
    errorTypeState: MutableState<PhoneEnterScreenErrorEnum>,
    nextButtonClickAction: ()->Unit
) {


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
                .size(164.dp, 164.dp)
        )

        SpacingVertical(heightDp = 36)
        Text(
            text = stringResource(id = R.string.phone_num_text),
            style = titleAuthTextStyle(),
            modifier = Modifier
                .padding(start=16.dp)
                .fillMaxWidth()
        )
        SpacingVertical(heightDp = 8)
        PhoneNumberInputField(
            phoneNumber,
            modifier = Modifier,
            imeAction = ImeAction.Done,
        )

        SpacingVertical(heightDp = 24)
        CommonButton(
            stringResource(R.string.next_screen_button_text),
            modifier = Modifier,
            enabledStatus = phoneNumValidationNextScreen(phoneNumber.value)
        ) {
            nextButtonClickAction()
        }

        if (openErrorDialogState.value) {
            val title = when (errorTypeState.value) {
                PhoneEnterScreenErrorEnum.RegistrationError -> stringResource(id = R.string.phone_enter_screen_registration_error_title)
                PhoneEnterScreenErrorEnum.ChangePassError -> stringResource(id = R.string.phone_enter_screen_change_password_error_title)
                PhoneEnterScreenErrorEnum.Else -> stringResource(id = R.string.something_wrong_tile)
            }
            val text = when (errorTypeState.value) {
                PhoneEnterScreenErrorEnum.RegistrationError -> stringResource(id = R.string.phone_enter_screen_registration_error_text)
                PhoneEnterScreenErrorEnum.ChangePassError -> stringResource(id = R.string.phone_enter_screen_change_password_error_text)
                PhoneEnterScreenErrorEnum.Else -> stringResource(id = R.string.something_wrong_text)
            }
            OneButtonDialog(title, text, openErrorDialogState)
        }
    }
}
