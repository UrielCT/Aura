package com.aura.domain

import com.aura.ui.RemoteDatabase
import com.aura.ui.models.City
import com.aura.ui.models.WeatherCity
import com.aura.ui.utils.FormatUtils
import com.aura.ui.utils.LocalDatabase
import com.aura.ui.utils.NetworkUtils
import kotlinx.coroutines.flow.Flow

class DataSource(
    private val rdb: RemoteDatabase,
    private val ldb: LocalDatabase,
    private val nUtils: NetworkUtils,
    private val fUtils:FormatUtils
) {
    //suspend fun getAllCities(onResult: (List<City>) -> Unit ) = ldb.getAllCities { onResult(it) }
    fun getAllCitiesRealTime():Flow<List<City>> = ldb.getAllCitiesRealTime()

    suspend fun searchWeatherByName(name:String, onResult:(WeatherCity?) ->Unit) {
        try {
            rdb.searchWeatherByName(name) { result ->
                onResult(fUtils.responseToWeatherCity(result))
            }
        } catch (e: Exception) {
            onResult(null)
        }
    }


        //rdb.searchWeatherByName(name){ onResult(it) }

    suspend fun addWeatherAndCity(weatherCity: WeatherCity, onResult: (Boolean) -> Unit) =
        ldb.addWeatherAndCity(weatherCity){ onResult(it) }

    suspend fun getWeatherByCity(city: City, onResult: (WeatherCity?) -> Unit){
        try {
            if(nUtils.isOnline()){
                //rdb.searchWeatherByName(city.name){ onResult(it) }
                rdb.getWeatherByCoordinates("${city.lat}, ${city.lon}"){ result ->
                    onResult(fUtils.responseToWeatherCity(result))
                }
            }else{
                ldb.getWeatherCityByCityId(city.id){ onResult(it) }
            }
        }catch (e: Exception){
            onResult(null)
        }

    }


}