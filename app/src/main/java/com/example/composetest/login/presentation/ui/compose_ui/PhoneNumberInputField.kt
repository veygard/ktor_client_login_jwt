package com.example.composetest.login.presentation.ui.compose_ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.text.isDigitsOnly
import com.example.composetest.login.R
import com.example.composetest.login.presentation.supports.visualTransformation.login.PhoneNumberTransformation
import com.example.composetest.login.presentation.ui.theme.Paragraph_16
import com.example.composetest.login.presentation.ui.theme.inputFieldsShapes
import com.example.composetest.login.util.LocalStuff

@ExperimentalComposeUiApi
@Composable
fun PhoneNumberInputField(
    phoneNumState: MutableState<String>,
    modifier: Modifier = Modifier,
    imeAction: ImeAction = ImeAction.Done,
    leadingIcon: @Composable (() -> Unit)? = {
        Image(
            painterResource(id = R.drawable.ic_phone),
            stringResource(id = R.string.contentDescription_icon_phone),
            modifier = Modifier.padding(start = 4.dp, top = 14.67.dp, bottom = 15.33.dp)
        )
    },
    moreOnDoneAction: () -> Unit = {},
    moreOnNextAction: () -> Unit = {},
    moreOnValueChanged: (value:String) -> Unit = {},
) {
    val focusManager = LocalStuff.getLocalFocusManager().current

    TextField(
        shape = inputFieldsShapes.medium,
        value = phoneNumState.value,
        onValueChange = { value ->
            if (value.length in 2..11 && value.isDigitsOnly()) {
                phoneNumState.value = value
                //делаем, чтобы 7-ка всегда была
            } else if (value.length <= 1 && value.isDigitsOnly()) {
                phoneNumState.value = "7"
            }
            moreOnValueChanged(value)
        },
        modifier = modifier
            .shadow(4.dp, inputFieldsShapes.medium)
            .fillMaxWidth()
            .clip(inputFieldsShapes.medium)
            .heightIn(54.dp),
        singleLine = true,
        maxLines = 1,
        placeholder = {
            Text(
                "+_(___)_______", style = Paragraph_16, color =  MaterialTheme.colors.secondary
            )
        },
        textStyle = Paragraph_16,
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = MaterialTheme.colors.surface,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            cursorColor =  MaterialTheme.colors.secondary,
            textColor = MaterialTheme.colors.onSurface
        ),
        leadingIcon = leadingIcon,
        keyboardOptions = KeyboardOptions(
            imeAction = imeAction,
            keyboardType = KeyboardType.NumberPassword
        ),
        visualTransformation = PhoneNumberTransformation(),
        keyboardActions = KeyboardActions(
            onDone = {
                focusManager.clearFocus()
                moreOnDoneAction()
            },
            onNext = {
                moreOnNextAction()
            }
        )
    )
}

@ExperimentalComposeUiApi
@Composable
@Preview
private fun PhoneFieldPreview() {
    val temp = remember { mutableStateOf("") }
    PhoneNumberInputField(
        phoneNumState = temp,
        modifier = Modifier.fillMaxWidth(),
        leadingIcon = null
    )
}
