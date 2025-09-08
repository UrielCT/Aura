package com.aura.data.datasource.remote

import com.aura.data.model.WeatherResponse
import com.aura.ui.utils.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RemoteDatabase(
    private val service: WeatherService,
) {

    suspend fun searchWeatherByName(name: String): WeatherResponse =
        withContext(Dispatchers.IO) {
            service.searchWeatherByName(
                key = Constants.API_KEY,
                name = name,
                lang = Constants.LANG_ES
            )
        }

    suspend fun getWeatherByCoordinates(coordinates: String): WeatherResponse =
        withContext(Dispatchers.IO) {
            service.getWeatherByCoordinates(
                key = Constants.API_KEY,
                coordinates = coordinates,
                lang = Constants.LANG_ES
            )
        }

}