package com.aura.data.datasource.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.aura.data.model.WeatherEntity
import com.aura.domain.model.WeatherCity
import com.aura.ui.utils.Constants

@Dao
interface WeatherDao {
    @Insert
    suspend fun addWeather(weatherEntity: WeatherEntity): Long

    @Update
    suspend fun updateWeather(weatherEntity: WeatherEntity): Int

    @Delete
    suspend fun deleteWeather(weatherEntity: WeatherEntity): Int

    @Query("SELECT * FROM ${Constants.E_WEATHER} " +
            "WHERE ${Constants.P_CITY_ID} = :cityId " +
            "LIMIT 1")
    suspend fun getWeatherByCityId(cityId: Long): WeatherEntity?

    @Query("SELECT * FROM ${Constants.E_WEATHER} " +
            "INNER JOIN ${Constants.E_CITY} " +
            "ON ${Constants.E_WEATHER}.cityId = ${Constants.E_CITY}.id " +
            "WHERE ${Constants.E_WEATHER}.cityId = :cityId " +
            "LIMIT 1")
    suspend fun getWeatherCityByCityId(cityId: Long): WeatherCity?
}