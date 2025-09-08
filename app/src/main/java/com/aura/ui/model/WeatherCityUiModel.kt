package com.aura.ui.model

data class WeatherCityUiModel(
    val name: String = "",
    val country: String = "",
    val tempText: String = "",
    val description: String = "",
    val windText: String = "",
    val iconUrl: String = "",
    val lat: Double = 0.0,
    val lon: Double = 0.0
)