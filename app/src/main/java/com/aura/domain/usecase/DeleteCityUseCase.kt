package com.aura.domain.usecase

import com.aura.domain.repository.WeatherRepository
import com.aura.ui.models.City

class DeleteCityUseCase( private val repository: WeatherRepository ) {
    suspend operator fun invoke(city: City) =
        repository.deleteCityAndWeather(city)
}
