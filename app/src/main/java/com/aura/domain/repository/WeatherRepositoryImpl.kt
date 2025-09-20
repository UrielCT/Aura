package com.aura.domain.repository

import com.aura.data.datasource.local.LocalDataSource
import com.aura.data.datasource.remote.RemoteDatabase
import com.aura.data.mappers.DataMappers
import com.aura.domain.mappers.DomainMappers
import com.aura.domain.model.City
import com.aura.domain.model.WeatherCity
import com.aura.ui.utils.FormatUtils
import com.aura.ui.utils.NetworkUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class WeatherRepositoryImpl(
    private val localDataSource: LocalDataSource,
    private val remoteDatabase: RemoteDatabase,
    private val networkUtils: NetworkUtils,
    private val formatUtils: FormatUtils,
    private val dataMappers: DataMappers,
    private val domainMappers: DomainMappers
) : WeatherRepository {

    //override fun getAllCitiesRealTime(): Flow<List<CityEntity>> = localDataSource.getAllCitiesRealTime()
    override fun getAllCitiesRealTime(): Flow<List<City>> =
        localDataSource.getAllCitiesRealTime().map { list -> list.map {
            dataMappers.cityEntityToCity(it) } }


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
        return localDataSource.getWeatherCityByCityId(cityId)
    }


    override suspend fun deleteCityAndWeather(city: City): Boolean {
        return localDataSource.deleteCityAndWeather( domainMappers.cityToCityEntity(city) )
    }



    override suspend fun searchWeatherByName(name: String): WeatherCity? {
        return try {
            if(name.isEmpty()){
                null
            }else{
                val response = remoteDatabase.searchWeatherByName(name)
                formatUtils.responseToWeatherCity(response)
            }
        }catch (e: Exception){
            null
        }
    }


    override suspend fun getWeatherByCity(city: City): WeatherCity? {
        return try {
            if (networkUtils.isOnline()) {
                val response = remoteDatabase.getWeatherByCoordinates("${city.lat}, ${city.lon}")
                formatUtils.responseToWeatherCity(response)
            } else {
                localDataSource.getWeatherCityByCityId(city.id)
            }
        } catch (e: Exception) {
            null
        }
    }


}