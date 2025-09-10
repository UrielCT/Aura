package com.aura.ui.utils

import com.aura.data.model.CityEntity
import com.aura.data.model.Condition
import com.aura.data.model.Current
import com.aura.data.model.WeatherResponse
import com.aura.domain.model.City
import com.aura.domain.model.WeatherCity
import com.aura.ui.model.CityUiModel
import com.aura.ui.model.WeatherCityUiModel


val cityEntities = listOf(
    CityEntity(name = "Cdmx", country = "México", lat = 19.4334565, lon = -99.1331708),
    CityEntity(name = "Madrid", country = "España", lat = 40.416775, lon = -3.703790)
)

val cityPreview = City(0,"Lima", "Perú", 1.2118, -2.1921)
val cityUiModel = CityUiModel(0, "Lima", "Perú", 1.2118, -2.1921)

fun getAllCityPreview() = listOf(
    City(1,"Cdmx", "México", 19.4334565, -99.1331708),
    City(2,"Madrid", "España", 40.416775, -3.703790),
    cityPreview
)


val weatherCityPreview = WeatherCity(
    31f, "Vientos fuertes",
    22.5f, "", "Lima", "Perú"
)

//fun getAllWeatherCityPreview() = listOf(
//    WeatherCity(
//        21f, "Nublado",
//        12.5f, "", "CDMX", "México"
//    ),
//    WeatherCity(
//        21f, "Soleado",
//        6f, "", "Madrid", "España"
//    ),
//    weatherCityPreview
//)

//Testing
//val weatherTest = Weather(
//    id = 1,
//    temp_c = 10f,
//    iconHttps = "https://cdn.weatherapi.com/weather/64x64/day/116.png",
//    description = "Templado",
//    wind_kph = 8f,
//    cityId = 1
//)

val weatherResponseTest = WeatherResponse(
    location = CityEntity(
        name = "Málaga",
        country = "España",
        lat = 36.72016,
        lon = -4.42034
    ),
    current = Current(
        temp_c = 29f,
        condition = Condition(
            text = "Parcialmente nublado.",
            icon = "//cdn.weatherapi.com/weather/64x64/day/116.png"
        ),
        wind_kph = 13.2f
    )
)

val weatherResponseNullIslandTest = WeatherResponse(
    location = CityEntity(
        name = "Isla Nula",
        country = "N/A",
        lat = 0.0,
        lon = 0.0
    ),
    current = Current(
        temp_c = 29f,
        condition = Condition(
            text = "Parcialmente nublado.",
            icon = "//cdn.weatherapi.com/weather/64x64/day/116.png"
        ),
        wind_kph = 13.2f
    )
)

val weatherCityTest = WeatherCity(
    temp_c = 29f,
    iconHttps = "https://cdn.weatherapi.com/weather/64x64/day/116.png",
    description="Parcialmente nublado.",
    wind_kph=13.2f,
    name="Berlin",
    country="DE",
    lat=36.72016,
    lon=-4.42034)

val weatherCityUiModel = WeatherCityUiModel(
    tempText = "25°C",
    description = "Function…",
    windText = "Viento: 10 km/h",
    iconUrl = "https://cdn.weatherapi.com/weather/64x64/day/116.png",
    name = "Berlin",
    country = "DE",
    lat = 36.72016,
    lon = -4.42034
)

val cityNullIslandTest = City(id = 1, name = "Isla Nula", country = "N/A", lat = 0.0, lon = 0.0)

val weatherCityNullIslandTest = weatherCityTest.copy(name = "Isla Nula", country = "N/A", lat = 0.0, lon = 0.0)
