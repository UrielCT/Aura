package com.aura.domain.repository

import com.aura.ui.models.City
import com.aura.ui.models.WeatherCity
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {
    fun getAllCitiesRealTime(): Flow<List<City>>
    suspend fun addWeatherAndCity(weatherCity: WeatherCity): Boolean
    suspend fun getWeatherCityByCityId(cityId: Long): WeatherCity?
    suspend fun deleteCityAndWeather(city: City): Boolean
    suspend fun searchWeatherByName(name: String): WeatherCity?
    suspend fun getWeatherByCity(city: City): WeatherCity?
}