package com.aura.ui.utils

import com.aura.dao.CityDao
import com.aura.dao.WeatherCityDao
import com.aura.dao.WeatherDao
import com.aura.ui.models.City
import com.aura.ui.models.WeatherCity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LocalDatabase(
    private val cityDao: CityDao,
    private val weatherDao: WeatherDao,
    private val weatherCityDao: WeatherCityDao,
    private val utils: FormatUtils
) {
    suspend fun getAllCities(onResult: (List<City>) -> Unit) = withContext(Dispatchers.IO){
        onResult(cityDao.getAllCities())
    }

    suspend fun addWeatherAndCity(weatherCity: WeatherCity, onResult: (Boolean) -> Unit)=
        withContext(Dispatchers.IO){
            val city = utils.weatherCityToCity(weatherCity)
            val weather = utils.weatherCityToWeather(weatherCity)
//            val result= cityDao.addCity(city)
//            val tempResult = weatherDao.addWeather(weather)
            val result = weatherCityDao.addCityAndWeather(city,weather)
            onResult(result > 0)
        }

    suspend fun getWeatherCityByCityId(cityId: Long, onResult: (WeatherCity?) -> Unit) =
        withContext(Dispatchers.IO){
            onResult(weatherDao.getWeatherCityByCityId(cityId))
        }
}