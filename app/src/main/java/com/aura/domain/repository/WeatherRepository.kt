package com.aura.domain.repository

import com.aura.domain.model.City
import com.aura.domain.model.WeatherCity
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {
    fun getAllCitiesRealTime(): Flow<List<City>>
    suspend fun addWeatherAndCity(weatherCity: WeatherCity): Boolean
    suspend fun getWeatherCityByCityId(cityId: Long): WeatherCity?
    suspend fun deleteCityAndWeather(city: City): Boolean
    suspend fun searchWeatherByName(name: String): WeatherCity?
    suspend fun getWeatherByCity(city: City): WeatherCity?
    suspend fun getCurrentVersion(): List<Int>
    suspend fun getMinAllowedVersion(): List<Int>
}