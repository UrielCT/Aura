package com.aura.data.datasource.remote

import com.aura.ui.models.WeatherResponse
import com.aura.ui.utils.Constants
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {
    @GET(Constants.PATH_V1)
    suspend fun searchWeatherByName(
        @Query(Constants.PARAM_KEY) key: String,
        @Query(Constants.PARAM_QUERY) name: String,
        @Query(Constants.PARAM_LANGUAGE) lang: String
    ) : WeatherResponse

    @GET(Constants.PATH_V1)
    suspend fun getWeatherByCoordinates(
        @Query(Constants.PARAM_KEY) key: String,
        @Query(Constants.PARAM_QUERY) coordinates: String,
        @Query(Constants.PARAM_LANGUAGE) lang: String
    ) : WeatherResponse
}