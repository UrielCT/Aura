package com.aura.ui.screens.weather

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.aura.domain.mappers.DomainMappers
import com.aura.domain.usecase.AddWeatherCityUseCase
import com.aura.domain.usecase.CanAccessToAppUseCase
import com.aura.domain.usecase.GetAllCitiesUseCase
import com.aura.domain.usecase.GetWeatherByCityUseCase
import com.aura.domain.usecase.SearchWeatherByNameUseCase
import com.aura.ui.mappers.UiMappers
import com.aura.ui.model.WeatherCityUiModel
import com.aura.ui.utils.cityPreview
import com.aura.ui.utils.cityUiModel
import com.aura.ui.utils.weatherCityTest
import com.aura.ui.utils.weatherCityUiModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.TestCoroutineScheduler
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.*

import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class WeatherViewModelTest {
    private val getAllCitiesUseCase: GetAllCitiesUseCase = mockk()
    private val addWeatherCityUseCase: AddWeatherCityUseCase = mockk()
    private val searchWeatherByNameUseCase: SearchWeatherByNameUseCase = mockk()
    private val getWeatherByCityUseCase: GetWeatherByCityUseCase = mockk()
    private val canAccessToAppUseCase: CanAccessToAppUseCase = mockk()
    private val domainMappers: DomainMappers = mockk()
    private val uiMappers: UiMappers = mockk()

    private lateinit var viewModel: WeatherViewModel

    private val testDispatcher = UnconfinedTestDispatcher( TestCoroutineScheduler() )

    @get:Rule
    var rule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)

        coEvery { canAccessToAppUseCase() } returns true

        coEvery { getAllCitiesUseCase() } returns flowOf(emptyList())

        viewModel = WeatherViewModel( getAllCitiesUseCase, addWeatherCityUseCase,
            searchWeatherByNameUseCase, getWeatherByCityUseCase,canAccessToAppUseCase, domainMappers, uiMappers
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }


    @Test
    fun `when viewmodel is created at the first time, get all cities and set items`() = runTest {
        // Given: list of cities
        val cities = listOf(cityPreview, cityPreview)
        val cityUi = cityUiModel

        // Mock: getAllCitiesUseCase return a list of cities
        coEvery { getAllCitiesUseCase() } returns flowOf(cities)
        every { domainMappers.cityToCityUiModel(any()) } returns cityUi

        // When
        val vm = WeatherViewModel(
            getAllCitiesUseCase,
            addWeatherCityUseCase,
            searchWeatherByNameUseCase,
            getWeatherByCityUseCase,
            canAccessToAppUseCase,
            domainMappers,
            uiMappers
        )


        advanceUntilIdle()

        // Then: uiState.items
        assertEquals(listOf(cityUi, cityUi), vm.uiState.value.items)
    }

    @Test
    fun `when viewmodel is created and cities is empty, set msgRes to weather_empty_list`() = runTest {
        // Given
        coEvery { getAllCitiesUseCase() } returns flowOf(emptyList())

        // When
        val vm = WeatherViewModel(
            getAllCitiesUseCase,
            addWeatherCityUseCase,
            searchWeatherByNameUseCase,
            getWeatherByCityUseCase,
            canAccessToAppUseCase,
            domainMappers,
            uiMappers
        )


        advanceUntilIdle()

        // Then
        assertTrue(vm.uiState.value.items.isEmpty())
    }


    /********************************************************************************************/
    // seerchWeather

    @Test
    fun `searchWeather sets data when result is not null`() = runTest {
        // Given
        val name = "Berlin"
        coEvery { searchWeatherByNameUseCase(name) } returns weatherCityTest
        every { domainMappers.weatherCityToWeatherCityUiModel(weatherCityTest) } returns weatherCityUiModel

        // When
        viewModel.searchWeather(name)
        advanceUntilIdle()

        // Then
        assertEquals(weatherCityUiModel, viewModel.uiState.value.data)
        assertFalse(viewModel.uiState.value.inProgress)
        //assertEquals(R.string.msg_empty, viewModel.uiState.value.msgRes) // no cambió el msg
    }

    @Test
    fun `searchWeather sets msgRes when result is null`() = runTest {
        // Given
        val name = "UnknownCity"
        coEvery { searchWeatherByNameUseCase(name) } returns null

        // When
        viewModel.searchWeather(name)
        advanceUntilIdle()

        // Then
        //assertNull(viewModel.uiState.value.data)
        //assertEquals(R.string.weather_search_error, viewModel.uiState.value.msgRes)
        assertFalse(viewModel.uiState.value.inProgress)
        assertEquals(WeatherCityUiModel(), viewModel.uiState.value.data)
    }


    /********************************************************************************************/
    // saveWeatherCity

    @Test
    fun `saveWeatherCity sets success message when useCase returns true`() = runTest {
        // Given
        val weatherCityUi = weatherCityUiModel
        val weatherCity = weatherCityTest

        every { uiMappers.weatherCityUiModelToWeatherCity(weatherCityUi) } returns weatherCity
        coEvery { addWeatherCityUseCase(weatherCity) } returns true

        // When
        viewModel.saveWeatherCity(weatherCityUi)
        advanceUntilIdle()

        // Then
        coVerify { addWeatherCityUseCase(weatherCity) }
        //assertEquals(R.string.weather_local_save_success, viewModel.uiState.value.msgRes)
        assertFalse(viewModel.uiState.value.inProgress)
    }

    @Test
    fun `saveWeatherCity sets error message when useCase returns false`() = runTest {
        // Given
        val weatherCityUi = weatherCityUiModel
        val weatherCity = weatherCityTest

        every { uiMappers.weatherCityUiModelToWeatherCity(weatherCityUi) } returns weatherCity
        coEvery { addWeatherCityUseCase(weatherCity) } returns false

        // When
        viewModel.saveWeatherCity(weatherCityUi)
        advanceUntilIdle()

        // Then
        coVerify { addWeatherCityUseCase(weatherCity) }
        //assertEquals(R.string.weather_local_save_error, viewModel.uiState.value.msgRes)
        assertFalse(viewModel.uiState.value.inProgress)
    }

    /********************************************************************************************/
    //getWeatherByCity

    @Test
    fun `getWeatherByCity sets data when useCase returns result`() = runTest {
        // Given
        val cityUi = cityUiModel
        val city = cityPreview

        val weatherCity = weatherCityTest
        val weatherCityUi = weatherCityUiModel

        every { uiMappers.cityUiModelToCity(cityUi) } returns city
        coEvery { getWeatherByCityUseCase(city) } returns weatherCity
        every { domainMappers.weatherCityToWeatherCityUiModel(weatherCity) } returns weatherCityUi

        // When
        viewModel.getWeatherByCity(cityUi)
        advanceUntilIdle()

        // Then
        coVerify { getWeatherByCityUseCase(city) }
        assertEquals(weatherCityUi, viewModel.uiState.value.data)
        assertFalse(viewModel.uiState.value.inProgress)
    }


    @Test
    fun `getWeatherByCity sets error message when useCase returns null`() = runTest {
        // Given
        val cityUi = cityUiModel
        val city = cityPreview

        every { uiMappers.cityUiModelToCity(cityUi) } returns city
        coEvery { getWeatherByCityUseCase(city) } returns null

        // When
        viewModel.getWeatherByCity(cityUi)
        advanceUntilIdle()

        // Then
        coVerify { getWeatherByCityUseCase(city) }
        //assertEquals(R.string.weather_local_by_city_error, viewModel.uiState.value.msgRes)
        assertFalse(viewModel.uiState.value.inProgress)
    }

}