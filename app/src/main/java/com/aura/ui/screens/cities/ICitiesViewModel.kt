package com.aura.ui.screens.cities

import com.aura.ui.models.City
import kotlinx.coroutines.flow.StateFlow

interface ICitiesViewModel {
    fun getUiState(): StateFlow<CityUiState>
    fun showMap(city: City)
    fun clearMsg()
    fun deleteCity(city: City)
}