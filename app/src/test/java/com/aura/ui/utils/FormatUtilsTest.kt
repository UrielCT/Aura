package com.aura.ui.utils

import com.aura.data.model.CityEntity
import com.aura.data.model.WeatherResponse
import org.junit.Assert.*

import org.junit.Test

class FormatUtilsTest {

    @Test
    fun responseToWeatherCitySuccess() {
        val utils = FormatUtils()
        val response = weatherResponseTest
        val result = utils.responseToWeatherCity(response)
        assertNotNull(result)
    }

    @Test
    fun responseToWeatherCityEmpty(){
        val utils = FormatUtils()
        val response = WeatherResponse()
        val result = utils.responseToWeatherCity(response)
        assert(result!!.name.isEmpty() && result.country.isEmpty())
    }

    @Test
    fun responseToWeatherCityFails() {
        val utils = FormatUtils()
        val response = weatherResponseTest.copy(location = CityEntity(lat = 200.0))
        val result = utils.responseToWeatherCity(response)
        assertNull(result)
    }

    @Test
    fun weatherCityToCityEntityMapsCorrectly() {
        val utils = FormatUtils()
        val weatherCity = weatherCityPreview

        val result = utils.weatherCityToCityEntity(weatherCity)

        assertEquals(weatherCity.name, result.name)
        assertEquals(weatherCity.country, result.country)
        assertEquals(weatherCity.lat, result.lat, 0.0)
        assertEquals(weatherCity.lon, result.lon, 0.0)
    }


    @Test
    fun weatherCityToWeatherEntityMapsCorrectly() {
        val utils = FormatUtils()
        val weatherCity = weatherCityPreview

        val result = utils.weatherCityToWeatherEntity(weatherCity)

        assertEquals(weatherCity.temp_c, result.temp_c, 0.0f)
        assertEquals(weatherCity.iconHttps, result.iconHttps)
        assertEquals(weatherCity.description, result.description)
        assertEquals(weatherCity.wind_kph, result.wind_kph, 0.0f)
    }

    /*********************************************************************************************/


}