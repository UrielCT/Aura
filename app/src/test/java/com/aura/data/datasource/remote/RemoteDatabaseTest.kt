package com.aura.data.datasource.remote

import com.aura.data.model.WeatherResponse
import com.aura.ui.utils.Constants
import com.aura.ui.utils.weatherResponseTest
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class RemoteDatabaseTest {
    private lateinit var mockService: WeatherService
    private lateinit var rdb: RemoteDatabase

    @Before
    fun setUp() {
        mockService = mock<WeatherService>()
        rdb = RemoteDatabase(mockService)
    }

    @Test
    fun searchWeatherByName() = runTest {
        whenever(mockService.searchWeatherByName(Constants.API_KEY,"CDMX",Constants.LANG_ES))
            .thenReturn(weatherResponseTest)
        //val rdb = RemoteDatabase( service = mockService, formatUtils = formatUtils)
        var result = WeatherResponse()
         result = rdb.searchWeatherByName("CDMX")
        assertNotNull(result)
        verify(mockService).searchWeatherByName(Constants.API_KEY,"CDMX",Constants.LANG_ES)
    }

    @Test
    fun getWeatherByCoordinates() = runTest {
        val emptyCoordintates = "0.0, 0.0"

        whenever( mockService.getWeatherByCoordinates( Constants.API_KEY, emptyCoordintates,
            Constants.LANG_ES ))
            .thenReturn(null)

        //val rdb = RemoteDatabase( service = mockService, formatUtils = formatUtils)
        var result: WeatherResponse? = WeatherResponse()
        result = rdb.getWeatherByCoordinates(emptyCoordintates)

        assertNull(result)
        verify(mockService).getWeatherByCoordinates(Constants.API_KEY, emptyCoordintates,
            Constants.LANG_ES)
    }
}