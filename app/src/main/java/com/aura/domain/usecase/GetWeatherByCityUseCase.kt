package com.aura.domain.usecase

import com.aura.domain.repository.WeatherRepository
import com.aura.domain.model.City
import com.aura.domain.model.WeatherCity

class GetWeatherByCityUseCase( private val repository: WeatherRepository ) {
    suspend operator fun invoke(city: City): WeatherCity? =
        repository.getWeatherByCity(city)
}