package com.aura.domain.usecase

import com.aura.domain.repository.WeatherRepository
import com.aura.ui.models.City
import com.aura.ui.models.WeatherCity

class GetWeatherByCityUseCase( private val repository: WeatherRepository ) {
    suspend operator fun invoke(city: City): WeatherCity? =
        repository.getWeatherByCity(city)
}