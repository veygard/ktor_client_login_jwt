package com.example.composetest.login.presentation.screen.otp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.example.composetest.login.R
import com.example.composetest.login.presentation.supports.extensions.formatPhone
import com.example.composetest.login.presentation.supports.text_validation.PhoneMask
import com.example.composetest.login.presentation.ui.compose_ui.OneButtonDialog
import com.example.composetest.login.presentation.ui.theme.Margin
import com.example.composetest.login.presentation.ui.theme.securityMeasuresTitleStyle
import com.example.composetest.login.util.SpacingVertical
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@ExperimentalComposeUiApi
@Composable
internal fun OtpScreenContent(
    phoneNumber: String,
    otpCodeIsEntered: (otp: String, openErrorDialogState: MutableState<Boolean>) -> Unit,
    sendOptAgain: () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()

    val openErrorDialogState = remember { mutableStateOf(false) }

    val otpFocusRequester = remember { FocusRequester() }
    val keyboard = LocalSoftwareKeyboardController.current
    val otpEditValue = remember { mutableStateOf("") }
    val scrollState = rememberScrollState()


    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
            .verticalScroll(scrollState)
            .padding(start = Margin.horizontalStandard, end = Margin.horizontalStandard)
    ) {
        SpacingVertical(32)

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = stringResource(
                    id = R.string.enter_sms_title_text,
                    phoneNumber.formatPhone(mask = PhoneMask.MASK_2)
                ),
                modifier = Modifier,
                style = securityMeasuresTitleStyle(),
                color = MaterialTheme.colors.secondary,
                textAlign = TextAlign.Center,
            )
            SpacingVertical(heightDp = 32)
            OtpInputField(
                Modifier.padding(
                    start = Margin.horizontalStandard,
                    end = Margin.horizontalStandard
                ),
                smsCodeLength = 5,
                whenFull = {
                    otpCodeIsEntered(it, openErrorDialogState)
                },
                otpFocusRequester,
                keyboard,
                otpEditValue,
                strokeWidth = 4f,
            )
            SpacingVertical(heightDp = 24)

            //таймер
            CountdownForNewSmSField(
                modifier = Modifier,
                moreActionOnResetTimerButton = {
                    sendOptAgain()
                },
            )
        }

        if (openErrorDialogState.value) {
            val title =
                stringResource(id = R.string.authorization_sms_error_dialog_title_text)
            val text = stringResource(id = R.string.authorization_sms_error_dialog_text)
            OneButtonDialog(title, text, openErrorDialogState,
                confirmAction = {
                    otpEditValue.value = ""
                    coroutineScope.launch {
                        keyboard?.hide() //без хайда почему-то не показывается
                        delay(300)
                        keyboard?.show()
                        otpFocusRequester.requestFocus()
                    }
                })
        }
    }
}
