package com.aura.domain.usecase

import com.aura.domain.repository.WeatherRepository
import com.aura.domain.model.WeatherCity

class SearchWeatherByNameUseCase( private val repository: WeatherRepository ) {
    suspend operator fun invoke(name: String): WeatherCity? =
        repository.searchWeatherByName(name)
}