package com.example.composetest.login.presentation.ui.compose_ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.composetest.login.presentation.ui.theme.roundShapes

@Composable
fun OneButtonDialog(
    title: String,
    text: String,
    isDialogOpen: MutableState<Boolean>,
    confirmAction: (()->Unit)? = null,
) {
    if (isDialogOpen.value) {
        AlertDialog(
            shape = roundShapes.medium,
            backgroundColor = MaterialTheme.colors.surface,
            onDismissRequest = {
                isDialogOpen.value = false
                confirmAction?.invoke()
            },
            title = {
                Column(
                    modifier = Modifier.fillMaxWidth(),
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
                    Modifier
                        .fillMaxWidth(),
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
                        .fillMaxHeight()
                        .fillMaxWidth(),
                    elevation = ButtonDefaults.elevation(0.dp),
                    onClick = {
                        isDialogOpen.value = false
                        confirmAction?.invoke()
                    },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color.Transparent
                    ),
                ) {
                    Text(text = "Окей", color =  MaterialTheme.colors.primary)
                }
            },
            dismissButton = {}
        )
    }
}
