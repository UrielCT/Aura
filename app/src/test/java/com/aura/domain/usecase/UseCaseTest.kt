package com.aura.domain.usecase

import com.aura.domain.repository.WeatherRepository
import com.cursosant.cursosant.common.model.cityNullIslandTest
import com.cursosant.cursosant.common.model.cityPreview
import com.cursosant.cursosant.common.model.getAllCityPreview
import com.cursosant.cursosant.common.model.weatherCityPreview
import com.cursosant.cursosant.common.model.weatherCityTest
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.*

import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class UseCaseTest {

    private val repository: WeatherRepository = mockk()
    private val addWeatherCityUseCase = AddWeatherCityUseCase(repository)
    private val deleteCityUseCase = DeleteCityUseCase(repository)

    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun addWeatherCityUseCaseShouldCallRepositoryAndReturnTrue() = runTest {
        val weatherCity = weatherCityPreview

        coEvery { repository.addWeatherAndCity(weatherCity) } returns true

        val result = addWeatherCityUseCase(weatherCity)

        coVerify(exactly = 1) { repository.addWeatherAndCity(weatherCity) }
        assertTrue(result)
    }

    @Test
    fun addWeatherCityUseCaseShouldReturnFalseIfRepositoryFails() = runTest {
        val weatherCity = weatherCityPreview

        coEvery { repository.addWeatherAndCity(weatherCity) } returns false

        val result = addWeatherCityUseCase(weatherCity)

        coVerify(exactly = 1) { repository.addWeatherAndCity(weatherCity) }
        assertFalse(result)
    }

    /*********************************************************************************************/

    @Test
    fun deleteCityUseCaseShouldCallRepositoryAndReturnTrue() = runTest {
        val city = cityPreview

        coEvery { repository.deleteCityAndWeather(city) } returns true

        val result = deleteCityUseCase(city)

        coVerify(exactly = 1) { repository.deleteCityAndWeather(city) }
        assertTrue(result)
    }

    @Test
    fun deleteCityUseCaseShouldReturnFalseIfRepositoryFails() = runTest {
        val city = cityPreview

        coEvery { repository.deleteCityAndWeather(city) } returns false

        val result = deleteCityUseCase(city)

        coVerify(exactly = 1) { repository.deleteCityAndWeather(city) }
        assertFalse(result)
    }

    /*********************************************************************************************/

    @Test
    fun getAllCitiesUseCaseShouldReturnFlowFromRepository() = runTest {
        // Given
        val fakeCities = getAllCityPreview()

        coEvery { repository.getAllCitiesRealTime() } returns flowOf( fakeCities )

        // When
        val useCase = GetAllCitiesUseCase(repository)
        val result = useCase().first()

        // Then
        coVerify(exactly = 1) { repository.getAllCitiesRealTime() }
        assertEquals(fakeCities, result)
    }

    @Test
    fun getAllCitiesUseCaseReturnsEmptyFlowWhenRepoEmpty() = runTest {
        coEvery { repository.getAllCitiesRealTime() } returns flowOf(emptyList())

        val useCase = GetAllCitiesUseCase(repository)
        val result = useCase().first()

        assertTrue(result.isEmpty())
    }

    /********************************************************************************************/

    @Test
    fun getWeatherByCityUseCaseReturnsWeatherCity() = runTest {
        val city = cityPreview
        val weatherCity = weatherCityTest

        coEvery { repository.getWeatherByCity(city) } returns weatherCity

        val useCase = GetWeatherByCityUseCase(repository)
        val result = useCase(city)

        assertEquals(weatherCity, result)
    }

    @Test
    fun getWeatherByCityUseCaseReturnsNullWhenRepoReturnsNull() = runTest {
        val city = cityNullIslandTest
        coEvery { repository.getWeatherByCity(city) } returns null

        val useCase = GetWeatherByCityUseCase(repository)
        val result = useCase(city)

        assertNull(result)
    }

    @Test(expected = RuntimeException::class)
    fun getWeatherByCityUseCaseThrowsExceptionWhenRepoRails() = runTest {
        val city = cityPreview
        coEvery { repository.getWeatherByCity(city) } throws RuntimeException("DB error")

        val useCase = GetWeatherByCityUseCase(repository)
        useCase(city)
    }

    /*********************************************************************************************/

    @Test
    fun searchWeatherByNameUseCaseReturnsWeatherCity() = runTest {
        val name = "Buenos Aires"
        val weatherCity = weatherCityTest

        coEvery { repository.searchWeatherByName(name) } returns weatherCity

        val useCase = SearchWeatherByNameUseCase(repository)
        val result = useCase(name)

        assertEquals(weatherCity, result)
    }

    @Test
    fun searchWeatherByNameUseCaseReturnsNullWhenRepoReturnsNull() = runTest {
        val name = "Nowhere"
        coEvery { repository.searchWeatherByName(name) } returns null

        val useCase = SearchWeatherByNameUseCase(repository)
        val result = useCase(name)

        assertNull(result)
    }

    @Test
    fun searchWeatherByNameUseCaseThrowsExceptionWhenRepoFails() = runTest {
        val name = "Buenos Aires"
        coEvery { repository.searchWeatherByName(name) } throws IllegalStateException("Network error")

        val useCase = SearchWeatherByNameUseCase(repository)

        try {
            useCase(name)
            fail("Exception expected")
        } catch (e: IllegalStateException) {
            assertEquals("Network error", e.message)
        }
    }


}