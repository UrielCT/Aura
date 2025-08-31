package com.aura.ui.screens.cities

import com.aura.dao.CityDao
import com.aura.dao.WeatherCityDao
import com.aura.ui.models.City
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class LocalDatabase(
    private val cityDao: CityDao,
    private val weatherCityDao: WeatherCityDao
) {
    fun gatAllCitiesRealTime(): Flow<List<City>> = cityDao.getAllCitiesRealTime()

    suspend fun deleteCityAndWeather(city: City, onResult: (Boolean) -> Unit) =
        withContext(Dispatchers.IO){
            onResult(weatherCityDao.deleteCityAndWeather(city) > 0)
        }

}