package com.aura.ui.screens.weather

import com.aura.R
import com.aura.ui.models.City
import com.aura.ui.models.WeatherCity

data class WeatherUiState(
    val data:WeatherCity = WeatherCity(),
    val items:List<City> = emptyList(),
    val inProgress:Boolean = false,
    val msgRes:Int = R.string.msg_empty
)
