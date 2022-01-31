package com.example.composetest.login.presentation.ui.compose_ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.composetest.login.R
import com.example.composetest.login.presentation.ui.theme.roundShapes

@Composable
fun TwoButtonsDialog(
    modifier: Modifier = Modifier,
    title: String,
    text: String,
    isDialogOpen: MutableState<Boolean>,
    confirmClick: () -> Unit = {},
    onDismissRequest: () -> Unit = {},
) {
    if (isDialogOpen.value) {
        AlertDialog(
            modifier = modifier,
            shape = roundShapes.medium,
            backgroundColor = MaterialTheme.colors.surface,
            onDismissRequest = onDismissRequest,
            title = {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                    ,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = title,
                        textAlign = TextAlign.Center
                    )
                }
            },
            text = {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = text,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.size(8.dp))
                    Divider(
                        color =  MaterialTheme.colors.secondary,
                        thickness = 0.5.dp,
                        modifier = Modifier
                            .height(0.5.dp)
                    )
                }
            },
            confirmButton = {
                Button(
                    modifier = Modifier
                        .fillMaxHeight(),
                    elevation = ButtonDefaults.elevation(0.dp),
                    onClick = {
                        isDialogOpen.value = false
                        confirmClick()
                    },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color.Transparent
                    ),
                ) {
                    Text(
                        text = stringResource(id = R.string.dialog_confirm_button),
                        color =  MaterialTheme.colors.error
                    )
                }
            },
            dismissButton = {
                Button(
                    modifier = Modifier
                        .fillMaxHeight(),
                    elevation = ButtonDefaults.elevation(0.dp),
                    onClick = {
                        isDialogOpen.value = false
                    },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color.Transparent
                    ),
                ) {
                    Text(
                        text = stringResource(id = R.string.dialog_dismiss_button),
                        color =  MaterialTheme.colors.primary
                    )
                }
            }
        )
    }
}
