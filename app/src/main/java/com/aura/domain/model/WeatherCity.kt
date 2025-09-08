package com.aura.domain.model

data class WeatherCity(
    val temp_c:Float = 0f,
    val description:String = "",
    val wind_kph:Float = 0f,
    val iconHttps: String = "",
    val name:String = "",
    val country:String = "",
    val lat: Double = 0.0,
    val lon: Double = 0.0
)
