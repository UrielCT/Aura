package com.aura.data.datasource.remote

import com.aura.ui.models.WeatherCity
import com.aura.ui.models.WeatherResponse
import com.aura.ui.utils.Constants
import com.aura.ui.utils.FormatUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RemoteDatabase(
    private val service: WeatherService,
    private val formatUtils: FormatUtils
) {


//    suspend fun searchWeatherByName(name: String, onResult: (WeatherResponse) -> Unit) =
//        withContext(Dispatchers.IO) {
//            val result = service.searchWeatherByName(
//                key = Constants.API_KEY,
//                name = name,
//                lang = Constants.LANG_ES
//            )
//            onResult(result)
//        }

    suspend fun searchWeatherByName(name: String): WeatherCity? = withContext(Dispatchers.IO) {
        try {
            val result = service.searchWeatherByName(
                key = Constants.API_KEY,
                name = name,
                lang = Constants.LANG_ES
            )
            formatUtils.responseToWeatherCity(result)
        } catch (e: Exception) {
            null
        }
    }

    suspend fun getWeatherByCoordinates(coordinates: String): WeatherCity? = withContext(Dispatchers.IO) {
        try {
            val result = service.getWeatherByCoordinates(
                key = Constants.API_KEY,
                coordinates = coordinates,
                lang = Constants.LANG_ES
            )
            formatUtils.responseToWeatherCity(result)
        } catch (e: Exception) {
            null
        }
    }

}