package com.aura.data.datasource.local

import com.aura.data.datasource.local.dao.CityDao
import com.aura.data.datasource.local.dao.WeatherCityDao
import com.aura.data.datasource.local.dao.WeatherDao
import com.aura.data.model.CityEntity
import com.aura.domain.model.WeatherCity
import com.aura.ui.utils.FormatUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class LocalDataSource(
    private val cityDao: CityDao,
    private val weatherDao: WeatherDao,
    private val weatherCityDao: WeatherCityDao,
    private val utils: FormatUtils
) {
    fun getAllCitiesRealTime(): Flow<List<CityEntity>> = cityDao.getAllCitiesRealTime()

    suspend fun addWeatherAndCity(weatherCity: WeatherCity, onResult: (Boolean) -> Unit)=
        withContext(Dispatchers.IO){
            val city = utils.weatherCityToCityEntity(weatherCity)
            val weather = utils.weatherCityToWeatherEntity(weatherCity)
            val result = weatherCityDao.addCityAndWeather(city, weather)
            onResult(result > 0)
        }

    suspend fun getWeatherCityByCityId(cityId: Long): WeatherCity? =
        withContext(Dispatchers.IO) {
            try {
                weatherDao.getWeatherCityByCityId(cityId)
            } catch (e: Exception) {
                null
            }
        }

    suspend fun deleteCityAndWeather(cityEntity: CityEntity): Boolean =
        withContext(Dispatchers.IO) {
            weatherCityDao.deleteCityAndWeather(cityEntity) > 0
        }

}