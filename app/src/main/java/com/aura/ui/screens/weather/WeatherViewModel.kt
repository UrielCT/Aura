package com.aura.ui.screens.weather

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aura.R
import com.aura.domain.DataSource
import com.aura.ui.models.City
import com.aura.ui.models.WeatherCity
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class WeatherViewModel(private val ds: DataSource): ViewModel() {

    private val _uiState = MutableStateFlow(WeatherUiState())
    val uiState: StateFlow<WeatherUiState> = _uiState.asStateFlow()

    init {
        getAllCities()
    }

    private fun getAllCities(){
        executeAction {
            ds.getAllCities { result ->
                if (result.isNotEmpty()){
                    _uiState.update { it.copy(items = result) }
                }else{
                    _uiState.update { it.copy(msgRes = R.string.weather_empty_list) }
                }
            }
        }
    }

    fun searchWeather(name: String){
        executeAction {
            ds.searchWeatherByName(name) { result ->
                if (result != null){
                    _uiState.update { it.copy(data = result) }
                }else{
                    _uiState.update { it.copy(msgRes = R.string.weather_search_error) }
                }
            }
        }
    }

    fun saveWeatherCity(weatherCity: WeatherCity){
        executeAction {
            ds.addWeatherAndCity(weatherCity){success ->
                if (success){
                    _uiState.update { it.copy(msgRes = R.string.weather_local_save_success) }
                }else{
                    _uiState.update { it.copy(msgRes = R.string.weather_local_save_error) }
                }
            }
        }
    }

    fun getWeatherByCity(city: City){
        executeAction {
            ds.getWeatherByCity(city){ result ->
                if(result != null){
                    _uiState.update { it.copy(data = result) }
                }else{
                    _uiState.update { it.copy(msgRes = R.string.weather_local_by_city_error) }
                }
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