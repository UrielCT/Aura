package com.aura.ui.screens.weather

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aura.R
import com.aura.domain.mappers.DomainMappers
import com.aura.domain.usecase.AddWeatherCityUseCase
import com.aura.domain.usecase.GetAllCitiesUseCase
import com.aura.domain.usecase.GetWeatherByCityUseCase
import com.aura.domain.usecase.SearchWeatherByNameUseCase
import com.aura.ui.mappers.UiMappers
import com.aura.ui.model.CityUiModel
import com.aura.ui.model.WeatherCityUiModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class WeatherViewModel(
    private val getAllCitiesUseCase: GetAllCitiesUseCase,
    private val addWeatherCityUseCase: AddWeatherCityUseCase,
    private val searchWeatherByNameUseCase: SearchWeatherByNameUseCase,
    private val getWeatherByCityUseCase: GetWeatherByCityUseCase,
    private val domainMappers: DomainMappers,
    private val uiMappers:UiMappers
): ViewModel() {

    private val _uiState = MutableStateFlow(WeatherUiState())
    val uiState: StateFlow<WeatherUiState> = _uiState.asStateFlow()

    init {
        getAllCitiesRealTime()
    }


    private fun getAllCitiesRealTime() {
        viewModelScope.launch {
            getAllCitiesUseCase().collect { cities ->
                if (cities.isNotEmpty()) {
                    _uiState.update { it.copy(items = cities.map {  city ->
                        domainMappers.cityToCityUiModel(city) }) }
                    //_uiState.update { it.copy(items = cities.map { it.toUiModel() }) }
                } else {
                    _uiState.update {
                        it.copy(
                            items = emptyList(),
                            msgRes = R.string.weather_empty_list
                        )
                    }
                }
            }
        }
    }


    fun searchWeather(name: String) {
        executeAction {
            val result = searchWeatherByNameUseCase(name)
            if (result != null) {
                _uiState.update { it.copy(data = domainMappers.weatherCityToWeatherCityUiModel(result) ) }
            } else {
                _uiState.update { it.copy(msgRes = R.string.weather_search_error) }
            }
        }
    }

    fun saveWeatherCity(weatherCityUi: WeatherCityUiModel) {
        executeAction {
            val success = addWeatherCityUseCase(
                uiMappers.weatherCityUiModelToWeatherCity(weatherCityUi) )
            if (success) {
                _uiState.update { it.copy(msgRes = R.string.weather_local_save_success) }
            } else {
                _uiState.update { it.copy(msgRes = R.string.weather_local_save_error) }
            }
        }
    }

    fun getWeatherByCity(cityUi: CityUiModel) {
        executeAction {
            val result = getWeatherByCityUseCase(uiMappers.cityUiModelToCity(cityUi))
            if (result != null) {
                _uiState.update {
                    it.copy(data = domainMappers.weatherCityToWeatherCityUiModel(result)) }
            } else {
                _uiState.update { it.copy(msgRes = R.string.weather_local_by_city_error) }
            }
        }
    }


    fun clearMsg(){
        viewModelScope.launch {
            _uiState.update { it.copy(msgRes = R.string.msg_empty) }
        }
    }


    private fun executeAction(block: suspend () -> Unit): Job{
        return viewModelScope.launch {
            _uiState.update { it.copy(inProgress = true) }
            try {
                block()
            }catch (e: Exception){
                _uiState.update { it.copy(msgRes = R.string.weather_general_error) }
            }finally {
                _uiState.update { it.copy(inProgress = false) }
            }
        }
    }
}