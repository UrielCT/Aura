package com.aura.ui.screens.weather

import com.aura.R
import com.aura.ui.models.WeatherCity

data class WeatherUiState(
    val date:WeatherCity = WeatherCity(),
    val inProgress:Boolean = false,
    val msgRes:Int = R.string.msg_empty
)
