package com.aura.ui.utils

import android.app.Application
import androidx.room.Room
import com.aura.AppDatabase
import com.aura.dao.CityDao
import com.aura.dao.WeatherCityDao
import com.aura.dao.WeatherDao
import org.koin.dsl.module

fun provideDatabase(application: Application):AppDatabase{
    return Room.databaseBuilder(
        application,
        AppDatabase::class.java,
        Constants.DB_NAME
    ).build()
}

fun provideCityDao(database: AppDatabase): CityDao= database.cityDao()
fun provideWeatherDao(database: AppDatabase): WeatherDao= database.weatherDao()
fun provideWeatherCityDao(database: AppDatabase): WeatherCityDao = database.weatherCityDao()

val localDatasourceModule = module {
    single { provideCityDao(get()) }
    single { provideWeatherDao(get()) }
    single { provideWeatherCityDao(get()) }
    single { provideDatabase(get()) }
}