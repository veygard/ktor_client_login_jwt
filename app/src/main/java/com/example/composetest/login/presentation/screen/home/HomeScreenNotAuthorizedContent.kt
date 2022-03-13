package com.example.composetest.login.presentation.screen.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.composetest.login.R
import com.example.composetest.login.presentation.ui.compose_ui.CommonButton
import com.example.composetest.login.presentation.ui.theme.H_L3
import com.example.composetest.login.presentation.ui.theme.Margin
import com.example.composetest.login.util.SpacingVertical

@Composable
internal fun HomeScreenNotAuthorizedContent(
    loginClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = Margin.horizontalStandard, end = Margin.horizontalStandard),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_home_empty),
            contentDescription = "home",
            alignment = Alignment.Center,
            modifier = Modifier
                .size(164.dp, 164.dp)
        )
        SpacingVertical(heightDp = 12)
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            Text(
                text = stringResource(id = R.string.home_screen_not_authorized_text),
                style = H_L3,
                color = MaterialTheme.colors.onBackground,
                modifier = Modifier.fillMaxWidth(0.7f)
            )

        }
        SpacingVertical(heightDp = 24)
        CommonButton(
            label = stringResource(id = R.string.home_screen_not_authorized_button),
            click = {
                loginClick()
            }
        )
    }
}
