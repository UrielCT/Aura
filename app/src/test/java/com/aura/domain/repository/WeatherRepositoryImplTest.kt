package com.aura.domain.repository

import com.aura.data.datasource.local.LocalDataSource
import com.aura.data.datasource.remote.RemoteDatabase
import com.aura.data.mappers.DataMappers
import com.aura.data.repository.WeatherRepositoryImpl
import com.aura.domain.mappers.DomainMappers
import com.aura.domain.model.WeatherCity
import com.aura.ui.utils.FormatUtils
import com.aura.ui.utils.NetworkUtils
import com.aura.ui.utils.cityEntities
import com.aura.ui.utils.cityNullIslandTest
import com.aura.ui.utils.cityPreview
import com.aura.ui.utils.weatherCityNullIslandTest
import com.aura.ui.utils.weatherCityPreview
import com.aura.ui.utils.weatherResponseNullIslandTest
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.TestCoroutineScheduler
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.*

import org.junit.After
import org.junit.Before
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.inject

@OptIn(ExperimentalCoroutinesApi::class)
class WeatherRepositoryImplTest : KoinTest{
    private val rdb: RemoteDatabase = mockk()
    private val ldb: LocalDataSource = mockk()
    private val nUtils: NetworkUtils = mockk()
   // private val fUtils: FormatUtils = mockk()

    private val repository: WeatherRepositoryImpl by inject()

    private val testDispatcher = UnconfinedTestDispatcher(TestCoroutineScheduler())

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        startKoin{
            modules(
                module{
                    single { FormatUtils() }
                    single { DataMappers() }
                    single { DomainMappers() }
                    single { nUtils }
                    single { ldb }
                    single { rdb }
                    single { WeatherRepositoryImpl( get(), get(), get(),
                        get(),get(), get(),get() ) }
                }
            )
        }
    }

    @After
    fun tearDown() {
        stopKoin()
        Dispatchers.resetMain()
    }


    @Test
    fun getAllCitiesRealTimeSuccess() = runTest {
        val cityList = cityEntities

        every { ldb.getAllCitiesRealTime() } returns flowOf(cityList)

        val result = repository.getAllCitiesRealTime().first()

        assertEquals(cityList.size, result.size)
        assertEquals(cityList[0].name, result[0].name)
        assertEquals(cityList[1].country, result[1].country)
    }

    @Test
    fun getAllCitiesRealTimeEmpty() = runTest {
        every { ldb.getAllCitiesRealTime() } returns flowOf(emptyList())

        val result = repository.getAllCitiesRealTime().first()

        assertTrue(result.isEmpty())
    }

    /*********************************************************************************************/

    @Test
    fun addWeatherAndCitySuccess() = runTest {
        val weatherCity = weatherCityPreview

        coEvery { ldb.addWeatherAndCity(weatherCity, any()) } answers {
            secondArg<(Boolean) -> Unit>().invoke(true)
        }

        val result = repository.addWeatherAndCity(weatherCity)

        coVerify { ldb.addWeatherAndCity(weatherCity, any()) }
        assertTrue(result)
    }

    @Test
    fun addWeatherAndCityFailure() = runTest {
        val weatherCity = weatherCityPreview

        coEvery { ldb.addWeatherAndCity(weatherCity, any()) } answers {
            secondArg<(Boolean) -> Unit>().invoke(false)
        }

        val result = repository.addWeatherAndCity(weatherCity)

        coVerify { ldb.addWeatherAndCity(weatherCity, any()) }
        assertFalse(result)
    }

    /*********************************************************************************************/

    @Test
    fun getWeatherCityByCityIdFound() = runTest {
        val cityId = 1L
        val expectedWeatherCity = weatherCityPreview

        coEvery { ldb.getWeatherCityByCityId(cityId) } returns expectedWeatherCity

        val result = repository.getWeatherCityByCityId(cityId)

        coVerify { ldb.getWeatherCityByCityId(cityId) }
        assertEquals(expectedWeatherCity, result)
    }

    @Test
    fun getWeatherCityByCityIdNotFound() = runTest {
        val cityId = 99L

        coEvery { ldb.getWeatherCityByCityId(cityId) } returns null

        val result = repository.getWeatherCityByCityId(cityId)

        coVerify { ldb.getWeatherCityByCityId(cityId) }
        assertNull(result)
    }

    /*********************************************************************************************/

    @Test
    fun deleteCityAndWeatherSuccess() = runTest {
        val city = cityPreview

        coEvery { ldb.deleteCityAndWeather(any()) } returns true

        val result = repository.deleteCityAndWeather(city)

        coVerify { ldb.deleteCityAndWeather(any()) }
        assertTrue(result)
    }

    @Test
    fun deleteCityAndWeatherFailure() = runTest {
        val city = cityPreview

        coEvery { ldb.deleteCityAndWeather(any()) } returns false

        val result = repository.deleteCityAndWeather(city)

        coVerify { ldb.deleteCityAndWeather(any()) }
        assertFalse(result)
    }

    /*********************************************************************************************/

    @Test
    fun getWeatherByCityOnlineSuccess() = runTest {
        val city = cityNullIslandTest
        val expectedWeatherCity = weatherCityNullIslandTest

        every { nUtils.isOnline() } returns true
        coEvery {
            rdb.getWeatherByCoordinates("${city.lat}, ${city.lon}")
        }returns weatherResponseNullIslandTest

        var result: WeatherCity? = null
        result = repository.getWeatherByCity(city)

        coVerify(exactly = 1) { rdb.getWeatherByCoordinates("${city.lat}, ${city.lon}") }
        coVerify(exactly = 0) { ldb.getWeatherCityByCityId(city.id) }

        assertEquals(expectedWeatherCity, result)
    }

    @Test
    fun getWeatherByCityOfflineSuccess() = runTest {
        val city = cityNullIslandTest
        val expectedWeatherCity = weatherCityNullIslandTest

        every { nUtils.isOnline() } returns false
        coEvery {
            ldb.getWeatherCityByCityId(city.id)
        }returns weatherCityNullIslandTest

        var result: WeatherCity? = null
        result = repository.getWeatherByCity(city)

        coVerify(exactly = 0) { rdb.getWeatherByCoordinates("${city.lat}, ${city.lon}") }
        coVerify(exactly = 1) { ldb.getWeatherCityByCityId(city.id) }

        assertEquals(expectedWeatherCity, result)
    }


    @Test
    fun getWeatherByCityOnlineFails() = runTest {
        val city = cityNullIslandTest

        every { nUtils.isOnline() } returns true
        coEvery {
            rdb.getWeatherByCoordinates("${city.lat}, ${city.lon}")
        }throws RuntimeException("API error")

        var result: WeatherCity? = WeatherCity()
        result = repository.getWeatherByCity(city)

        coVerify(exactly = 1) { rdb.getWeatherByCoordinates("${city.lat}, ${city.lon}") }
        coVerify(exactly = 0) { ldb.getWeatherCityByCityId(city.id) }

        assertNull( result )
    }

    @Test
    fun getWeatherByCityOfflineFails() = runTest {
        val city = cityNullIslandTest

        every { nUtils.isOnline() } returns false
        coEvery {
            ldb.getWeatherCityByCityId(city.id)
        }throws RuntimeException("API error")

        var result: WeatherCity? = null
        result = repository.getWeatherByCity(city)

        coVerify(exactly = 0) { rdb.getWeatherByCoordinates("${city.lat}, ${city.lon}") }
        coVerify(exactly = 1) { ldb.getWeatherCityByCityId(city.id) }

        assertNull( result )
    }

    /*********************************************************************************************/

    @Test
    fun searchWeatherByNameSuccess() = runTest {
        val name = "Málaga"
        val expectedWeatherCity = weatherCityNullIslandTest

        coEvery { rdb.searchWeatherByName(name) } returns weatherResponseNullIslandTest

        val result = repository.searchWeatherByName(name)

        coVerify(exactly = 1) { rdb.searchWeatherByName(name) }
        assertEquals(expectedWeatherCity, result)
    }


    @Test
    fun searchWeatherByNameCheckEmptyReturnsNull() = runTest {
        val name= ""

        var result: WeatherCity? = WeatherCity()
        result = repository.searchWeatherByName(name)

        coVerify(exactly = 0) { rdb.searchWeatherByName(name) }
        assertNull(result)
    }

    @Test
    fun searchWeatherByNameApiFails() = runTest {
        val name = "NullIsland"

        coEvery { rdb.searchWeatherByName(name) } throws RuntimeException("API error")

        val result = repository.searchWeatherByName(name)

        coVerify(exactly = 1) { rdb.searchWeatherByName(name) }
        assertNull(result)
    }


}