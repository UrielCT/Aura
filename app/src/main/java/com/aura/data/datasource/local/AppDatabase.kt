package com.aura.data.datasource.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.aura.data.datasource.local.dao.CityDao
import com.aura.data.datasource.local.dao.WeatherCityDao
import com.aura.data.datasource.local.dao.WeatherDao
import com.aura.data.model.CityEntity
import com.aura.data.model.WeatherEntity
import com.aura.ui.utils.Constants

@Database(entities = [CityEntity::class, WeatherEntity::class], version = Constants.DB_INIT_VERSION)
abstract class AppDatabase: RoomDatabase() {
    abstract fun cityDao(): CityDao
    abstract fun weatherDao(): WeatherDao
    abstract fun weatherCityDao(): WeatherCityDao
}