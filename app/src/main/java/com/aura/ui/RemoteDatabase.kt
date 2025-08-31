package com.aura.ui

import com.aura.ui.models.WeatherResponse
import com.aura.ui.utils.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RemoteDatabase(
    private val service:WeatherService
) {

//    suspend fun searchWeatherByName(name: String, onResult: (WeatherCity?) -> Unit) =
//        withContext(Dispatchers.IO) {
//            try {
//                val result = service.searchWeatherByName(
//                    key = Constants.API_KEY,
//                    name = name,
//                    lang = Constants.LANG_ES
//                )
//                onResult(utils.responseToWeatherCity(result))
//            } catch (e: Exception) {
//                onResult(null)
//            }
//        }
    suspend fun searchWeatherByName(name: String, onResult: (WeatherResponse) -> Unit) =
        withContext(Dispatchers.IO) {
            val result = service.searchWeatherByName(
                key = Constants.API_KEY,
                name = name,
                lang = Constants.LANG_ES
            )
            onResult(result)
        }

    suspend fun getWeatherByCoordinates(coordinates: String, onResult: (WeatherResponse) -> Unit) =
        withContext(Dispatchers.IO) {
            val result = service.getWeatherByCoordinates(
                key = Constants.API_KEY,
                coordinates = coordinates,
                lang = Constants.LANG_ES
            )
            onResult(result)
        }
}