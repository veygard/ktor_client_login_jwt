package com.example.composetest.login.presentation.ui.compose_ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.height
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.composetest.login.presentation.ui.theme.buttonShapes
import com.example.composetest.login.presentation.ui.theme.buttonTextStyle

@Composable
fun TransparentBackgroundButton(
    label: String,
    textColor: Color,
    modifier: Modifier = Modifier,
    click: () -> Unit
) {
    Button(
        modifier = modifier,
        onClick = click,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color.Transparent
        ),
        border = BorderStroke(0.dp, Color.Transparent),
        elevation = ButtonDefaults.elevation(0.dp),
        shape = buttonShapes.small

    ) {
        Text(
            text = label,
            modifier = Modifier
                .height(24.dp),
            style = (buttonTextStyle), color = textColor
        )

    }
}