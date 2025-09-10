package com.aura.ui.screens.cities

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aura.R
import com.aura.domain.mappers.DomainMappers
import com.aura.domain.usecase.DeleteCityUseCase
import com.aura.domain.usecase.GetAllCitiesUseCase
import com.aura.ui.mappers.UiMappers
import com.aura.ui.model.CityUiModel
import com.aura.ui.utils.IntentUtils
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CitiesViewModel(
    private val getAllCitiesUseCase: GetAllCitiesUseCase,
    private val deleteCityAndWeatherUseCase: DeleteCityUseCase,
    private val utils: IntentUtils,
    private val domainMappers:DomainMappers,
    private val uiMappers:UiMappers
) : ViewModel(), ICitiesViewModel {

    private val _uiState = MutableStateFlow(CityUiState())
    override fun getUiState(): StateFlow<CityUiState> = _uiState.asStateFlow()

    init {
        getAllCitiesRealTime()
    }

    private fun getAllCitiesRealTime() {
        viewModelScope.launch {
            getAllCitiesUseCase().collect { result ->
                if (result.isNotEmpty()) {
                    _uiState.update { it.copy(items = result.map {city ->
                        domainMappers.cityToCityUiModel(city) }) }
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


    override fun deleteCity(cityEntity: CityUiModel) {
        executeAction {
            val success = deleteCityAndWeatherUseCase(uiMappers.cityUiModelToCity(cityEntity))
            if (success) {
                _uiState.update { it.copy(msgRes = R.string.cities_msg_delete_success) }
            } else {
                _uiState.update { it.copy(msgRes = R.string.cities_msg_delete_error) }
            }
        }
    }


    override fun showMap(cityEntity: CityUiModel){
        utils.showMap(cityEntity.lat, cityEntity.lon, cityEntity.toString())
    }

    override fun clearMsg(){
        viewModelScope.launch {
            _uiState.update { it.copy(msgRes = R.string.msg_empty) }
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