package com.aura.ui.screens.weather

import com.aura.R
import com.aura.ui.model.CityUiModel
import com.aura.ui.model.WeatherCityUiModel

data class WeatherUiState(
    val items: List<CityUiModel> = emptyList(),
    val data: WeatherCityUiModel? = WeatherCityUiModel(),
    val inProgress: Boolean = false,
    val msgRes: Int = R.string.msg_empty
)
