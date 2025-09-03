package com.aura.data.datasource.local.dao

import androidx.room.Dao
import androidx.room.Transaction
import com.aura.data.model.CityEntity
import com.aura.data.model.WeatherEntity

@Dao
interface WeatherCityDao : CityDao, WeatherDao {
    @Transaction
    suspend fun addCityAndWeather(cityEntity: CityEntity, weatherEntity: WeatherEntity):Long{
        val dbCity = getCityByNameAndCountry(cityEntity.name, cityEntity.country)
        if (dbCity == null){
            return addWeather(weatherEntity.copy(cityId = addCity(cityEntity)))
        }else{
            getWeatherByCityId(dbCity.id)?.let { dbWeather ->
                return updateWeather(weatherEntity.copy(id = dbWeather.id, cityId = dbWeather.cityId)).toLong()
            }
        }
        return 0
    }

    @Transaction
    suspend fun deleteCityAndWeather(cityEntity: CityEntity):Int{
        getWeatherByCityId(cityId = cityEntity.id)?.let { weather ->
            deleteWeather(weather)
            return deleteCity(cityEntity)
        }
        return 0
    }
}