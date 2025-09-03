package com.aura.domain.usecase

import com.aura.domain.model.City
import com.aura.domain.repository.WeatherRepository
import kotlinx.coroutines.flow.Flow

class GetAllCitiesUseCase(private val repository: WeatherRepository) {
    operator fun invoke(): Flow<List<City>> = repository.getAllCitiesRealTime()
}
