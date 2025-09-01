package com.aura.domain.usecase

import com.aura.domain.repository.WeatherRepository

class GetAllCitiesUseCase(private val repository: WeatherRepository) {
    operator fun invoke() = repository.getAllCitiesRealTime()
}
