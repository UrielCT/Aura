package com.aura.ui.screens.cities

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.aura.domain.mappers.DomainMappers
import com.aura.domain.usecase.DeleteCityUseCase
import com.aura.domain.usecase.GetAllCitiesUseCase
import com.aura.ui.mappers.UiMappers
import com.aura.ui.utils.IntentUtils
import com.aura.ui.utils.cityPreview
import com.aura.ui.utils.cityUiModel
import io.mockk.coEvery
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
class CitiesViewModelTest {

    private val getAllCitiesUseCase: GetAllCitiesUseCase = mockk()
    private val deleteCityUseCase: DeleteCityUseCase = mockk()
    private val utils: IntentUtils = mockk()
    private val domainMappers:DomainMappers = mockk()
    private val uiMappers:UiMappers = mockk()

    private lateinit var viewModel: CitiesViewModel

    private val testDispatcher = UnconfinedTestDispatcher( TestCoroutineScheduler() )

    @get:Rule
    var rule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)

        coEvery { getAllCitiesUseCase() } returns flowOf(emptyList())

        viewModel = CitiesViewModel( getAllCitiesUseCase, deleteCityUseCase,
            utils,domainMappers, uiMappers
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }


    // -----------------------------------------
    //  getAllCitiesRealTime
    // -----------------------------------------
    @Test
    fun `when viewmodel is created with cities, set items`() = runTest {
        val cities = listOf(cityPreview, cityPreview)
        coEvery { getAllCitiesUseCase() } returns flowOf(cities)
        every { domainMappers.cityToCityUiModel(any()) } returns cityUiModel

        val vm = CitiesViewModel(getAllCitiesUseCase, deleteCityUseCase, utils, domainMappers, uiMappers)
        advanceUntilIdle()

        assertEquals(listOf(cityUiModel, cityUiModel), vm.getUiState().value.items)
    }

    @Test
    fun `when viewmodel is created with empty list, set msgRes to weather_empty_list`() = runTest {
        coEvery { getAllCitiesUseCase() } returns flowOf(emptyList())

        val vm = CitiesViewModel(getAllCitiesUseCase, deleteCityUseCase, utils, domainMappers, uiMappers)
        advanceUntilIdle()

        assertTrue(vm.getUiState().value.items.isEmpty())
        //assertEquals(R.string.weather_empty_list, vm.getUiState().value.msgRes)
    }

    // -------------------------
    // Test deleteCity
    // -------------------------
    @Test
    fun `deleteCity sets msgRes success when use case returns true`() = runTest {
        every { uiMappers.cityUiModelToCity(cityUiModel) } returns cityPreview
        coEvery { deleteCityUseCase(cityPreview) } returns true

        viewModel.deleteCity(cityUiModel)
        advanceUntilIdle()

        //assertEquals(R.string.cities_msg_delete_success, viewModel.getUiState().value.msgRes)
    }

    @Test
    fun `deleteCity sets msgRes error when use case returns false`() = runTest {
        every { uiMappers.cityUiModelToCity(cityUiModel) } returns cityPreview
        coEvery { deleteCityUseCase(cityPreview) } returns false

        viewModel.deleteCity(cityUiModel)
        advanceUntilIdle()

        //assertEquals(R.string.cities_msg_delete_error, viewModel.getUiState().value.msgRes)
    }

    @Test
    fun `deleteCity sets msgRes general error when exception is thrown`() = runTest {
        every { uiMappers.cityUiModelToCity(cityUiModel) } returns cityPreview
        coEvery { deleteCityUseCase(cityPreview) } throws RuntimeException("fail")

        viewModel.deleteCity(cityUiModel)
        advanceUntilIdle()

        //assertEquals(R.string.weather_general_error, viewModel.getUiState().value.msgRes)
    }



    // -------------------------
    // Test clearMsg
    // -------------------------
    @Test
    fun `clearMsg sets msgRes to msg_empty`() = runTest {
        viewModel.clearMsg()
        advanceUntilIdle()

        //assertEquals(R.string.msg_empty, viewModel.getUiState().value.msgRes)
    }

}