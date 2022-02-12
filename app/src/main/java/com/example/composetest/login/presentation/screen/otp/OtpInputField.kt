package com.example.composetest.login.presentation.screen.otp

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.*
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.composetest.login.presentation.ui.theme.H_L3


@ExperimentalComposeUiApi
@Composable
fun OtpInputField(
    modifier: Modifier = Modifier,
    smsCodeLength: Int = 4,
    whenFull: (smsCode: String) -> Unit,
    otpFocusRequester: FocusRequester,
    keyboard: SoftwareKeyboardController?,
    editValue: MutableState<String>,
    strokeWidth: Float = 6.0f,
) {
    val otpLength = remember { smsCodeLength }

    //скрытое поле ввода, которое всегда имеет фокус
    TextField(
        value = editValue.value,
        onValueChange = {
            when{
                it.length < otpLength && checkDigitsOnly(it)-> {editValue.value = it}
                it.length == otpLength && checkDigitsOnly(it) -> {
                    editValue.value = it
                    whenFull(editValue.value)
                }
            }

        },
        modifier = Modifier
            .size(0.dp)
            .border(0.dp, Color.Blue)
            .focusRequester(otpFocusRequester),
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Number
        )
    )

    //Боксы с цифрами
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        (0 until otpLength).map { index ->
            OtpCell(
                modifier = Modifier
                    .weight(1f)
                    .clickable {
                        otpFocusRequester.requestFocus()
                        keyboard?.show()
                    },
                value = editValue.value.getOrNull(index)?.toString() ?: "",
                currentBlock = mutableStateOf(editValue.value.length == index),
                isValueFiled = mutableStateOf(editValue.value.length >= (index + 1)),
                strokeWidth = strokeWidth
            )
            Spacer(modifier = Modifier.size(8.dp))
        }
    }

    //даём фокус в текст-филд и открываем клаву
    DisposableEffect(Unit) {
        keyboard?.show()
        otpFocusRequester.requestFocus()
        onDispose { }
    }

}

@Composable
fun OtpCell(
    modifier: Modifier,
    value: String,
    currentBlock: MutableState<Boolean>,
    isValueFiled: MutableState<Boolean>,
    strokeWidth:Float
) {
    val filledColor = MaterialTheme.colors.primary
    val notFilledColor = MaterialTheme.colors.secondaryVariant

    val animationSpec = getAnimationSpec(currentBlock, isValueFiled)

    //первый пустой блок имеет повторяющуюся анимацию смены цвета
    val animateBottomLineColor by animateColorAsState(
        if (currentBlock.value && !isValueFiled.value) filledColor else notFilledColor,
        animationSpec = animationSpec
    )


    Box(
        modifier = modifier
            .height(48.dp)
            .drawBehind {
                val y = size.height - strokeWidth / 2
                drawLine(
                    //первый пустой блок имеет повторяющуюся анимацию смены цвета
                    //блок с цифрами - основной цвет
                    //блок второй+ пустой блок - серый
                    when{
                        currentBlock.value && !isValueFiled.value-> animateBottomLineColor
                        !currentBlock.value && !isValueFiled.value -> notFilledColor
                        !currentBlock.value && isValueFiled.value -> filledColor
                        else -> notFilledColor
                    },
                    Offset(0f, y),
                    Offset(size.width, y),
                    strokeWidth
                )
            },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = if (currentBlock.value) "" else value,
            style = H_L3,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}


private fun getAnimationSpec(
    currentBlock: MutableState<Boolean>,
    isValueFiled: MutableState<Boolean>
): AnimationSpec<Color> {
    return when {
        currentBlock.value && !isValueFiled.value -> {
            infiniteRepeatable(
                animation = tween(durationMillis = 400),
                repeatMode = RepeatMode.Reverse
            )
        }
        else -> {
            snap(delayMillis = 100)
        }
    }
}

private fun checkDigitsOnly(enteredNumbers: String): Boolean {
    var check = true
    enteredNumbers.forEach {
        if (!it.isDigit()) {
            check = false
        }
    }
    return check
}
