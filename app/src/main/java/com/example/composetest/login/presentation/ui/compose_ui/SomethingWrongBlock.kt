package com.example.composetest.login.presentation.ui.compose_ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.composetest.login.R
import com.example.composetest.login.presentation.ui.theme.H_L3
import com.example.composetest.login.presentation.ui.theme.Paragraph_16_Medium
import com.example.composetest.login.util.SpacingVertical

@Composable
fun SomethingWrongBlock() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background),
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Image(
                painter = painterResource(id = R.drawable.anguish_smile),
                contentDescription = null,
                modifier = Modifier.size(152.dp)
            )
            SpacingVertical(heightDp = 48.0)
            Text(
                text = stringResource(id = R.string.something_wrong_title),
                style = H_L3,
                color = MaterialTheme.colors.onSurface,
                textAlign = TextAlign.Center
            )
            SpacingVertical(24.0)
            Text(
                text = stringResource(id = R.string.something_wrong_text),
                style = Paragraph_16_Medium,
                color = MaterialTheme.colors.onSurface,
                textAlign = TextAlign.Center
            )
            SpacingVertical(12.0)
        }
    }
}
