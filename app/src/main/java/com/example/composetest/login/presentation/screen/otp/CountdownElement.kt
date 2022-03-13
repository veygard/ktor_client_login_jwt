package com.example.composetest.login.presentation.screen.otp

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.composetest.login.R
import com.example.composetest.login.presentation.ui.compose_ui.TransparentBackgroundButton
import com.example.composetest.login.presentation.ui.theme.buttonTextStyle
import kotlinx.coroutines.delay

@Composable
fun CountdownForNewSmSField(
    modifier: Modifier = Modifier,
    timer:Int = 10,
    moreActionOnResetTimerButton: () -> Unit = {},
) {

    var timerValue by remember { mutableStateOf(timer) }
    var timerIsRunning by remember { mutableStateOf(true) }

    LaunchedEffect(key1 = timerValue, key2 = timerIsRunning, block = {
        when {
            timerValue > 0 && timerIsRunning -> {
                delay(1000L)
                timerValue -= 1
            }
            timerValue == 0 -> {
                timerIsRunning = false
            }
        }
    })

    val min = timerValue / 60
    val sec = (timerValue - min * 60)
    val secStr = if (sec < 10) {
        "0$sec"
    } else sec
    Box(
        modifier = modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        if (timerIsRunning) {
            CountdownText(
                stringResource(
                    id =
                    R.string.countdown_send_sms_again_text
                ) + " " + min.toString() + ":" + secStr
            )
        } else {
            SendSmsAgainButton  (click = {
                moreActionOnResetTimerButton()
                timerValue = timer
                timerIsRunning = true
            })
        }
    }
}

@Composable
private fun SendSmsAgainButton(click: () -> Unit) {
    TransparentBackgroundButton(
        label = stringResource(id = R.string.send_sms_again_text),
        textColor =  MaterialTheme.colors.primary,
        modifier = Modifier
    ) {
        click()
    }
}

@Composable
private fun CountdownText(text: String) {
    Text(
        text = text,
        style = buttonTextStyle,
        color =  MaterialTheme.colors.primary
    )
}
