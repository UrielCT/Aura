package com.aura.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.stringResource
import com.aura.ui.theme.CommonPaddingDefault
import com.aura.ui.theme.CommonPaddingMin
import com.aura.ui.utils.Constants
import kotlinx.coroutines.delay

@Composable
fun CustomSnackBar(
    modifier: Modifier = Modifier,
    msgRes: Int,
    backgroundColor: Color = Color.Transparent,
    textColor: Color = MaterialTheme.colorScheme.onPrimary,
    shape: Shape = RectangleShape,
    isPreview: Boolean = false,
    duration: Long = Constants.DURATION_SHORT,
    onDismiss: () -> Unit
) {
    var showSnackBar by remember { mutableStateOf(isPreview) }
    val msg = stringResource(msgRes)

    LaunchedEffect(msgRes) {
        if (msg.isNotBlank()) {
            showSnackBar = true
            delay(duration)
            onDismiss()
            showSnackBar = false
        }
    }

    Box(
        modifier = modifier,
        contentAlignment = Alignment.BottomCenter // 👈 aparece abajo de la pantalla
    ) {
        AnimatedVisibility(
            visible = msg.isNotBlank() && showSnackBar,
            enter = slideInVertically(
                initialOffsetY = { fullHeight -> fullHeight } // empieza abajo
            ) + fadeIn(),
            exit = slideOutVertically(
                targetOffsetY = { fullHeight -> fullHeight } // sale hacia abajo
            ) + fadeOut()
        ) {
            Text(
                text = msg,
                color = textColor,
                modifier = Modifier
                    .background(color = backgroundColor, shape = shape)
                    .padding(
                        vertical = CommonPaddingMin,
                        horizontal = CommonPaddingDefault
                    )
            )
        }
    }
}
