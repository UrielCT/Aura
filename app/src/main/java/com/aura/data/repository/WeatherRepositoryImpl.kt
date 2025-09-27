package com.aura.data.repository

import android.content.Context
import com.aura.domain.remote.RemoteConfigProvider
import com.aura.data.datasource.local.LocalDataSource
import com.aura.data.datasource.remote.RemoteDatabase
import com.aura.data.mappers.DataMappers
import com.aura.domain.mappers.DomainMappers
import com.aura.domain.model.City
import com.aura.domain.model.WeatherCity
import com.aura.domain.repository.WeatherRepository
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
    private val domainMappers: DomainMappers,
    private val context: Context,
    private val remoteConfigProvider: RemoteConfigProvider // 👈 inyectado
) : WeatherRepository {

    companion object {
        const val MIN_VERSION = "min_version"
    }

    override fun getAllCitiesRealTime(): Flow<List<City>> =
        localDataSource.getAllCitiesRealTime().map { list ->
            list.map { dataMappers.cityEntityToCity(it) }
        }

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
        return localDataSource.deleteCityAndWeather(domainMappers.cityToCityEntity(city))
    }

    override suspend fun searchWeatherByName(name: String): WeatherCity? {
        return try {
            if (name.isEmpty()) {
                null
            } else {
                val response = remoteDatabase.searchWeatherByName(name)
                formatUtils.responseToWeatherCity(response)
            }
        } catch (e: Exception) {
            null
        }
    }

    override suspend fun getWeatherByCity(city: City): WeatherCity? {
        return try {
            if (networkUtils.isOnline()) {
                val response =
                    remoteDatabase.getWeatherByCoordinates("${city.lat}, ${city.lon}")
                formatUtils.responseToWeatherCity(response)
            } else {
                localDataSource.getWeatherCityByCityId(city.id)
            }
        } catch (e: Exception) {
            null
        }
    }

    override suspend fun getCurrentVersion(): List<Int> {
        val packageInfo = context.packageManager.getPackageInfo(context.packageName, 0)
        val versionName = packageInfo.versionName ?: "1.0.0"
        return versionName.split(".").map { it.toIntOrNull() ?: 0 }
    }

    override suspend fun getMinAllowedVersion(): List<Int> {
        val minVersionString = remoteConfigProvider.getMinVersion()
        return minVersionString.split(".").map { it.toIntOrNull() ?: 0 }
    }
}


//class WeatherRepositoryImpl(
//    private val localDataSource: LocalDataSource,
//    private val remoteDatabase: RemoteDatabase,
//    private val networkUtils: NetworkUtils,
//    private val formatUtils: FormatUtils,
//    private val dataMappers: DataMappers,
//    private val domainMappers: DomainMappers,
//    private val context: Context
//) : WeatherRepository {
//
//    companion object{
//        const val MIN_VERSION = "min_version"
//    }
//
//    private val remoteConfig:FirebaseRemoteConfig = Firebase.remoteConfig.apply {
//        setConfigSettingsAsync(remoteConfigSettings { minimumFetchIntervalInSeconds = 3600 }) // cambiar a 3600
//        fetchAndActivate()
//    }
//
//    override fun getAllCitiesRealTime(): Flow<List<City>> =
//        localDataSource.getAllCitiesRealTime().map { list -> list.map {
//            dataMappers.cityEntityToCity(it) } }
//
//
//    override suspend fun addWeatherAndCity(weatherCity: WeatherCity): Boolean {
//        return withContext(Dispatchers.IO) {
//            var success = false
//            localDataSource.addWeatherAndCity(weatherCity) { result ->
//                success = result
//            }
//            success
//        }
//    }
//
//    override suspend fun getWeatherCityByCityId(cityId: Long): WeatherCity? {
//        return localDataSource.getWeatherCityByCityId(cityId)
//    }
//
//
//    override suspend fun deleteCityAndWeather(city: City): Boolean {
//        return localDataSource.deleteCityAndWeather( domainMappers.cityToCityEntity(city) )
//    }
//
//
//
//    override suspend fun searchWeatherByName(name: String): WeatherCity? {
//        return try {
//            if(name.isEmpty()){
//                null
//            }else{
//                val response = remoteDatabase.searchWeatherByName(name)
//                formatUtils.responseToWeatherCity(response)
//            }
//        }catch (e: Exception){
//            null
//        }
//    }
//
//
//    override suspend fun getWeatherByCity(city: City): WeatherCity? {
//        return try {
//            if (networkUtils.isOnline()) {
//                val response = remoteDatabase.getWeatherByCoordinates("${city.lat}, ${city.lon}")
//                formatUtils.responseToWeatherCity(response)
//            } else {
//                localDataSource.getWeatherCityByCityId(city.id)
//            }
//        } catch (e: Exception) {
//            null
//        }
//    }
//
//
//    override suspend fun getCurrentVersion(): List<Int> {
//        val packageInfo = context.packageManager.getPackageInfo(context.packageName, 0)
//        val versionName = packageInfo.versionName ?: "1.0.0"
//        return versionName.split(".").map { it.toIntOrNull() ?: 0 }
//    }
//
//    override suspend fun getMinAllowedVersion(): List<Int> {
//        val minVersionString = remoteConfig.getString(MIN_VERSION)
//        return minVersionString.split(".").map { it.toIntOrNull() ?: 0 }
//    }
//}