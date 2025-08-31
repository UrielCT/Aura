package com.aura

import androidx.room.Database
import androidx.room.RoomDatabase
import com.aura.dao.CityDao
import com.aura.dao.WeatherCityDao
import com.aura.dao.WeatherDao
import com.aura.ui.models.City
import com.aura.ui.models.Weather
import com.aura.ui.utils.Constants

@Database(entities = [City::class, Weather::class], version = Constants.DB_INIT_VERSION)
abstract class AppDatabase: RoomDatabase() {
    abstract fun cityDao(): CityDao
    abstract fun weatherDao(): WeatherDao
    abstract fun weatherCityDao(): WeatherCityDao
}