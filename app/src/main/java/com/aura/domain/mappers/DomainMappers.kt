package com.aura.domain.mappers

import com.aura.data.model.CityEntity
import com.aura.domain.model.City
import com.aura.domain.model.WeatherCity
import com.aura.ui.model.CityUiModel
import com.aura.ui.model.WeatherCityUiModel

class DomainMappers {

    fun cityToCityUiModel(city: City):  CityUiModel{
        return CityUiModel(
            id = city.id,
            name = city.name,
            country = city.country,
            lat = city.lat,
            lon = city.lon
        )
    }

    fun cityToCityEntity(city: City): CityEntity{
        return CityEntity(
            id = city.id,
            name = city.name,
            country = city.country,
            lat = city.lat,
            lon = city.lon
        )
    }

    fun weatherCityToWeatherCityUiModel(weatherCity: WeatherCity): WeatherCityUiModel{
        return WeatherCityUiModel(
            name = weatherCity.name,
            country = weatherCity.country,
            tempText = "${weatherCity.temp_c}°C",
            description = weatherCity.description,
            windText = "Viento: ${weatherCity.wind_kph} km/h",
            iconUrl = weatherCity.iconHttps,
            lat = weatherCity.lat,
            lon = weatherCity.lon
        )

    }

}