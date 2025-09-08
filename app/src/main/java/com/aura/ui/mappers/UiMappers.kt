package com.aura.ui.mappers

import com.aura.domain.model.City
import com.aura.domain.model.WeatherCity
import com.aura.ui.model.CityUiModel
import com.aura.ui.model.WeatherCityUiModel

class UiMappers {

    fun cityUiModelToCity(cityUiModel: CityUiModel): City {
        return City(
            id = cityUiModel.id,
            name = cityUiModel.name,
            country = cityUiModel.country,
            lat = cityUiModel.lat,
            lon = cityUiModel.lon
        )
    }

    fun weatherCityUiModelToWeatherCity(wCiUiModel: WeatherCityUiModel): WeatherCity {
        return WeatherCity(
            temp_c = (wCiUiModel.tempText.replace("°C", "").toDoubleOrNull() ?: 0.0).toFloat(),
            description = wCiUiModel.description,
            wind_kph = (wCiUiModel.windText.replace("Viento:", "")
                .replace("km/h", "")
                .trim()
                .toDoubleOrNull() ?: 0.0).toFloat(),
            iconHttps = wCiUiModel.iconUrl,
            name = wCiUiModel.name,
            country = wCiUiModel.country,
            lat = wCiUiModel.lat,
            lon = wCiUiModel.lon
        )
    }

}