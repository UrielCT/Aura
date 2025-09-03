package com.aura.ui.screens.cities

import com.aura.ui.model.CityUiModel
import kotlinx.coroutines.flow.StateFlow

interface ICitiesViewModel {
    fun getUiState(): StateFlow<CityUiState>
    fun showMap(cityEntity: CityUiModel)
    fun clearMsg()
    fun deleteCity(cityEntity: CityUiModel)
}