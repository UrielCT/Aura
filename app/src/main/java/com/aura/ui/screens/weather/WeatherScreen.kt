package com.aura.ui.screens.weather

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.aura.R

@Composable
fun WeatherScreen(modifier: Modifier){
    Text(stringResource(R.string.weather_title))
}