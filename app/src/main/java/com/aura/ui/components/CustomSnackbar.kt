package com.aura.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.aura.R
import com.aura.ui.theme.CommonPaddingDefault
import com.aura.ui.theme.CommonPaddingMin
import com.aura.ui.utils.Constants
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun CustomSnackbar(
    modifier: Modifier = Modifier,
    msgRes: Int,
    backgroundColor: Color = Color.Transparent,
    shape: Shape = RectangleShape,
    isPreview: Boolean = false,
    duration:Long = Constants.DURATION_SHORT,
    onDismiss: () -> Unit
) {
    val scope= rememberCoroutineScope()
    var showSnackbar by remember { mutableStateOf(isPreview) }
    val msg = stringResource(msgRes)

    LaunchedEffect(msgRes) {
        scope.launch {
            showSnackbar = true
            delay(duration)
            onDismiss()
            showSnackbar = false
        }
    }
    Box(modifier = modifier,
        contentAlignment = Alignment.Center
    ){
        AnimatedVisibility(visible = msg.isNotBlank() && showSnackbar) {
            Text(text = msg,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .background(color = backgroundColor, shape = shape)
                    .padding(vertical = CommonPaddingMin, horizontal = CommonPaddingDefault))
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun CustomSnackbar(){
    MaterialTheme{
        CustomSnackbar(msgRes = R.string.app_name,
            isPreview = true,
            backgroundColor = Color.Blue,
            shape = CircleShape) { }
    }
}