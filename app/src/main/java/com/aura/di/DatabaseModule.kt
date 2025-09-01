package com.aura.di

import android.app.Application
import androidx.room.Room
import com.aura.data.datasource.local.AppDatabase
import com.aura.data.datasource.local.LocalDataSource
import com.aura.data.datasource.local.dao.CityDao
import com.aura.data.datasource.local.dao.WeatherCityDao
import com.aura.data.datasource.local.dao.WeatherDao
import com.aura.data.datasource.remote.RemoteDatabase
import com.aura.ui.utils.Constants
import org.koin.dsl.module

fun provideDatabase(application: Application): AppDatabase {
    return Room.databaseBuilder(
        application,
        AppDatabase::class.java,
        Constants.DB_NAME
    ).build()
}

fun provideCityDao(database: AppDatabase): CityDao = database.cityDao()
fun provideWeatherDao(database: AppDatabase): WeatherDao = database.weatherDao()
fun provideWeatherCityDao(database: AppDatabase): WeatherCityDao = database.weatherCityDao()

val databaseModule = module {
    single { RemoteDatabase( get(), get() ) }
    single { LocalDataSource( get(), get(), get(), get()) }

    single { provideCityDao(get()) }
    single { provideWeatherDao(get()) }
    single { provideWeatherCityDao(get()) }

    single { provideDatabase(get()) }
}