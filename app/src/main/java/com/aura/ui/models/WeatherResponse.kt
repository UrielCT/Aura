package com.aura.ui.models

data class WeatherResponse(
    val location: Location= Location(),
    val current: Current= Current()
)
