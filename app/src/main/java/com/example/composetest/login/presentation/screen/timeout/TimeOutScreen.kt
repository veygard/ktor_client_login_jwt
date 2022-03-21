package com.example.composetest.login.presentation.screen.timeout

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.composetest.login.R
import com.example.composetest.login.presentation.screen.destinations.LoginScreenDestination
import com.example.composetest.login.presentation.ui.compose_ui.CommonButton
import com.example.composetest.login.presentation.ui.theme.H_L3
import com.example.composetest.login.presentation.ui.theme.Margin
import com.example.composetest.login.presentation.ui.theme.Paragraph_16_Medium
import com.example.composetest.login.util.SpacingVertical
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@ExperimentalComposeUiApi
@ExperimentalMaterialApi
@Composable
@Destination
fun TimeOutScreen(
    navigator: DestinationsNavigator,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .padding(start = Margin.horizontalStandard, end = Margin.horizontalStandard)
            .background(MaterialTheme.colors.background),
    ) {


        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            SpacingVertical(heightDp = 24)
            Icon(
                painter = painterResource(id = R.drawable.ic_no_wifi_svgrepo_com),
                contentDescription = null,
                tint = MaterialTheme.colors.primary,
                modifier = Modifier.size(128.dp)
            )
            SpacingVertical(heightDp = 24.0)
            Text(
                text = stringResource(id = R.string.timeout_title),
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
            SpacingVertical(24.0)
            CommonButton(label = "Перейти на страницу логина") {
                navigator.navigate(LoginScreenDestination)
            }

        }
    }
}
