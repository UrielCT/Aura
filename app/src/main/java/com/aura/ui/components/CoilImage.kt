package com.aura.ui.components

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BrokenImage
import androidx.compose.material.icons.filled.Timer
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import org.koin.compose.koinInject

@Composable
fun CoilImage(
    url:String,
    modifier: Modifier = Modifier,
    shape: Shape = CircleShape,
    imageLoader: ImageLoader = koinInject()
){
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(url)
            .crossfade(true)
            .memoryCachePolicy(CachePolicy.ENABLED)
            .diskCachePolicy(CachePolicy.ENABLED)
            .memoryCacheKey(url)
            .diskCacheKey(url)
            .build(),
        imageLoader=imageLoader,
        contentDescription = null,
        placeholder = rememberVectorPainter(Icons.Default.Timer),
        error = rememberVectorPainter(Icons.Default.BrokenImage),
        contentScale = ContentScale.Crop,
        modifier = modifier.clip(shape)
    )
}