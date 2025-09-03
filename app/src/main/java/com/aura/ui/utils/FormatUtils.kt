package com.aura.ui.utils

import com.aura.data.model.CityEntity
import com.aura.data.model.WeatherEntity
import com.aura.domain.model.WeatherCity
import com.aura.data.model.WeatherResponse

class FormatUtils {
    fun responseToWeatherCity(response: WeatherResponse): WeatherCity? {
        try {
            if(response.location.lat > 90 || response.location.lat < -90 ||
                response.location.lon > 180 || response.location.lon < -180 ) throw Exception()

            val weatherCity = WeatherCity(
                temp_c = response.current.temp_c,
                description = response.current.condition.text,
                wind_kph = response.current.wind_kph,
                iconHttps = response.current.condition.iconHttps,
                name = response.location.name,
                country = response.location.country,
                lat = response.location.lat,
                lon = response.location.lon
            )
            return weatherCity
        }catch (e: Exception){
            return null
        }
    }




    fun weatherCityToCityEntity(weatherCity: WeatherCity): CityEntity {
        return CityEntity(
            name = weatherCity.name,
            country = weatherCity.country,
            lat = weatherCity.lat,
            lon = weatherCity.lon
        )
    }

    fun weatherCityToWeatherEntity(weatherCity: WeatherCity) : WeatherEntity {
        return WeatherEntity(
            temp_c = weatherCity.temp_c,
            iconHttps = weatherCity.iconHttps,
            description = weatherCity.description,
            wind_kph = weatherCity.wind_kph
        )
    }
}