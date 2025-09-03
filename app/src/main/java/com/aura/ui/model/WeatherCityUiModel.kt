package com.aura.ui.model

import com.aura.domain.model.WeatherCity

data class WeatherCityUiModel(
    val name: String = "",
    val country: String = "",
    val tempText: String = "",
    val description: String = "",
    val windText: String = "",
    val iconUrl: String = "",
    val lat: Double = 0.0,
    val lon: Double = 0.0
){

    fun toWeatherCity(): WeatherCity {
        return WeatherCity(
            temp_c = (tempText.replace("°C", "").toDoubleOrNull() ?: 0.0).toFloat(),
            description = description,
            wind_kph = (windText.replace("Viento:", "")
                .replace("km/h", "")
                .trim()
                .toDoubleOrNull() ?: 0.0).toFloat(),
            iconHttps = iconUrl,
            name = name,
            country = country,
            lat = lat,
            lon = lon
        )
    }

}