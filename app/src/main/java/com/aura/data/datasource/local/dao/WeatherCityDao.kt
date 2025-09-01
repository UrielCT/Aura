package com.aura.data.datasource.local.dao

import androidx.room.Dao
import androidx.room.Transaction
import com.aura.ui.models.City
import com.aura.ui.models.Weather

@Dao
interface WeatherCityDao : CityDao, WeatherDao {
    @Transaction
    suspend fun addCityAndWeather(city: City, weather: Weather):Long{
        val dbCity = getCityByNameAndCountry(city.name, city.country)
        if (dbCity == null){
            return addWeather(weather.copy(cityId = addCity(city)))
        }else{
            getWeatherByCityId(dbCity.id)?.let { dbWeather ->
                return updateWeather(weather.copy(id = dbWeather.id, cityId = dbWeather.cityId)).toLong()
            }
        }
        return 0
    }

    @Transaction
    suspend fun deleteCityAndWeather(city: City):Int{
        getWeatherByCityId(cityId = city.id)?.let { weather ->
            deleteWeather(weather)
            return deleteCity(city)
        }
        return 0
    }
}