package com.aura.ui.models

data class WeatherResponse(
    val location: City= City(),
    val current: Current= Current()
)
