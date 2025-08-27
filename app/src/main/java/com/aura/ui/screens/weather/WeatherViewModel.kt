package com.aura.ui.screens.weather

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aura.R
import com.aura.ui.RemoteDatabase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class WeatherViewModel(private val rdb: RemoteDatabase): ViewModel() {

    private val _uiState = MutableStateFlow(WeatherUiState())
    val uiState: StateFlow<WeatherUiState> = _uiState.asStateFlow()

    fun searchWeather(name: String){
        viewModelScope.launch {
            _uiState.update { it.copy(inProgress = true) }
            try {
                rdb.searchWeatherByName(name) { result ->
                    if (result != null){
                        _uiState.update { it.copy(date = result) }
                    }else{
                        _uiState.update { it.copy(msgRes = R.string.weather_search_error) }
                    }
                }
            }catch (e: Exception){
                _uiState.update { it.copy(msgRes = R.string.weather_general_error) }
            }finally {
                _uiState.update { it.copy(inProgress = false) }
            }
        }
    }
}