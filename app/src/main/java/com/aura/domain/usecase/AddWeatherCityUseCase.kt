package com.aura.domain.usecase

import com.aura.domain.repository.WeatherRepository
import com.aura.domain.model.WeatherCity

class AddWeatherCityUseCase( private val repository: WeatherRepository ) {
    suspend operator fun invoke(weatherCity: WeatherCity) =
        repository.addWeatherAndCity(weatherCity)
}