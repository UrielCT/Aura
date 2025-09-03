package com.aura.data.model

data class WeatherResponse(
    val location: CityEntity = CityEntity(),
    val current: Current = Current()
)
