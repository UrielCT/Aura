package com.aura.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.aura.R
import com.aura.ui.screens.weather.navigateToPlayStore
import com.aura.ui.theme.CommonPaddingMiddle
import com.aura.ui.theme.CommonPaddingMin
import com.aura.ui.theme.FontSizeLange
import com.aura.ui.theme.FontSizeMin
import com.aura.ui.theme.VersionDialogHeight

@Composable
fun VersionDialog() {
    val context = LocalContext.current
    Dialog(
        onDismissRequest = {},
        properties = DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false)
    ) {
        Card(colors = CardDefaults.cardColors(containerColor = Color.White)) {
            Column(
                modifier = Modifier
                    .padding(CommonPaddingMiddle)
                    .fillMaxWidth()
                    .height(VersionDialogHeight),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(R.string.update_version_msg),
                    fontSize = FontSizeLange,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(CommonPaddingMiddle))
                Text(
                    text = stringResource(R.string.update_version_description),
                    fontSize = FontSizeMin,
                    color = Color.Gray,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.weight(1f))
                Button(onClick = { navigateToPlayStore(context) }) {
                    Text(text = stringResource(R.string.update_version_btn))
                }
                Spacer(modifier = Modifier.height(CommonPaddingMin))
            }
        }

    }
}