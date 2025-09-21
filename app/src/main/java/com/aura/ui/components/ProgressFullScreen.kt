package com.aura.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.aura.ui.theme.ProgressBackground

@Composable
fun ProgressFullScreen(visible:Boolean = false){
    AnimatedVisibility(
        visible = visible,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        Box(Modifier
            .fillMaxSize()
            .background(ProgressBackground)
            .clickable(interactionSource = null, indication = null){},
            contentAlignment = Alignment.Center){
            CircularProgressIndicator()
        }
    }
}