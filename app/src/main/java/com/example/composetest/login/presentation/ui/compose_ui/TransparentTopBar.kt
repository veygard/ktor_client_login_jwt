package com.example.composetest.login.presentation.ui.compose_ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.example.composetest.login.presentation.ui.theme.H_L3

@Composable
fun TransparentTopBar(title: String, backIconsShow: Boolean = true, click: () -> Unit = {}) {

    TopAppBar(
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = title,
                    style = H_L3
                )
            }
        },
        modifier = Modifier
            .height(36.dp),
        actions = {
            if (backIconsShow) {
                IconButton(onClick = {
                }) {
                    Box(
                        contentAlignment = Alignment.BottomCenter,
                        modifier = Modifier.size(36.dp)
                    ) {
                        CloseButton {
                            click()
                        }
                    }
                }
            }
        },
        backgroundColor = MaterialTheme.colors.background,
        elevation = 0.dp
    )
}
