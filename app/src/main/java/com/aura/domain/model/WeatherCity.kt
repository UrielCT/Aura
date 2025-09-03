package com.aura.domain.model

import com.aura.ui.model.WeatherCityUiModel

data class WeatherCity(
    val temp_c:Float = 0f,
    val description:String = "",
    val wind_kph:Float = 0f,
    val iconHttps: String = "",
    val name:String = "",
    val country:String = "",
    val lat: Double = 0.0,
    val lon: Double = 0.0
){
    fun toWeatherCityUiModel(): WeatherCityUiModel = WeatherCityUiModel(
        name = name,
        country = country,
        tempText = "${temp_c}°C",
        description = description,
        windText = "Viento: ${wind_kph} km/h",
        iconUrl = iconHttps,
        lat = lat,
        lon = lon
    )
}
