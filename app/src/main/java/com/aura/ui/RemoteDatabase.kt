package com.aura.ui

import com.aura.ui.models.WeatherCity
import com.aura.ui.utils.Constants
import com.aura.ui.utils.FormatUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RemoteDatabase(
    private val service:WeatherService,
    private val utils: FormatUtils
) {

    suspend fun searchWeatherByName(name:String, onResult:(WeatherCity?) ->Unit)=
        withContext(Dispatchers.IO){
            try {
                val result = service.searchWeatherByName(
                    key = Constants.API_KEY,
                    name = name,
                    lang = Constants.LANG_ES
                )
                onResult(utils.responseToWeatherCity(result))
            }catch (e: Exception){
                onResult(null)
            }
        }
}