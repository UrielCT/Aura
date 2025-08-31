package com.aura.ui.screens.cities

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aura.R
import com.aura.ui.models.City
import com.aura.ui.utils.IntentUtils
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CitiesViewModel(
    private val db: LocalDatabase,
    private val utils: IntentUtils
) : ViewModel(), ICitiesViewModel {
    private val _uiState = MutableStateFlow(CityUiState())
    override fun getUiState(): StateFlow<CityUiState> = _uiState.asStateFlow()

    init {
        getAllCitiesRealTime()
    }

    private fun getAllCitiesRealTime(){
        viewModelScope.launch {
            db.gatAllCitiesRealTime().collect { result ->
                if (result.isNotEmpty()){
                    _uiState.update { it.copy(items = result) }
                }else{
                    _uiState.update { it.copy( items = emptyList(),
                        msgRes = R.string.cities_msg_empty_list) }
                }
            }
        }
    }

    override fun showMap(city: City){
        utils.showMap(city.lat,city.lon,city.name)
    }


    override fun clearMsg(){
        viewModelScope.launch {
            _uiState.update { it.copy(msgRes = R.string.msg_empty) }
        }
    }

    override fun deleteCity(city: City) {
        executeAction {
            db.deleteCityAndWeather(city){ success ->
                if (success){
                    _uiState.update { it.copy(msgRes = R.string.cities_msg_delete_success) }
                }else{
                    _uiState.update { it.copy(msgRes = R.string.cities_msg_delete_error) }
                }
            }
        }
    }

    private fun executeAction(block: suspend () -> Unit): Job {
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