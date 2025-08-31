package com.aura.ui.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.aura.R

@Composable
fun DialogInfo(
    infoRes: Int,
    titleRes: Int,
    confirmRes:Int = R.string.dialog_ok,
    onDismissRequest: (Boolean) -> Unit
){
    AlertDialog(
        onDismissRequest = { onDismissRequest(false) },
        title = { Text(stringResource(titleRes)) },
        text = { Text(stringResource(infoRes)) },
        confirmButton = {
            TextButton(onClick = { onDismissRequest(true) }) {
                Text(stringResource(confirmRes))
            }
        },
        dismissButton = {
            TextButton(onClick = { onDismissRequest(false) }) {
                Text(stringResource(R.string.dialog_cancel))
            }
        }

    )
}