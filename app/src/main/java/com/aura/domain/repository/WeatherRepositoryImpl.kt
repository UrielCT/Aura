package com.aura.domain.repository

import com.aura.data.datasource.local.LocalDataSource
import com.aura.data.datasource.remote.RemoteDatabase
import com.aura.ui.models.City
import com.aura.ui.models.WeatherCity
import com.aura.ui.utils.NetworkUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class WeatherRepositoryImpl(
    private val localDataSource: LocalDataSource,
    private val remoteDatabase: RemoteDatabase,
    private val networkUtils: NetworkUtils,
) : WeatherRepository {

    override fun getAllCitiesRealTime(): Flow<List<City>> = localDataSource.getAllCitiesRealTime()


    override suspend fun addWeatherAndCity(weatherCity: WeatherCity): Boolean {
        return withContext(Dispatchers.IO) {
            var success = false
            localDataSource.addWeatherAndCity(weatherCity) { result ->
                success = result
            }
            success
        }
    }

    override suspend fun getWeatherCityByCityId(cityId: Long): WeatherCity? {
        val result: WeatherCity? = localDataSource.getWeatherCityByCityId(cityId)
        return result
    }


    override suspend fun deleteCityAndWeather(city: City): Boolean {
        return localDataSource.deleteCityAndWeather(city)
    }



    override suspend fun searchWeatherByName(name: String): WeatherCity? {
        return remoteDatabase.searchWeatherByName(name)
    }



    override suspend fun getWeatherByCity(city: City): WeatherCity? {
        return withContext(Dispatchers.IO){
            try {
                if(networkUtils.isOnline()){
                    //rdb.searchWeatherByName(city.name){ onResult(it) }
                    remoteDatabase.getWeatherByCoordinates("${city.lat}, ${city.lon}")
                }else{
                    localDataSource.getWeatherCityByCityId(city.id)
                }
            }catch (e: Exception){
                null
            }
        }
    }


}