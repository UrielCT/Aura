package com.aura.di

import com.aura.domain.usecase.AddWeatherCityUseCase
import com.aura.domain.usecase.DeleteCityUseCase
import com.aura.domain.usecase.GetAllCitiesUseCase
import com.aura.domain.usecase.GetWeatherByCityUseCase
import com.aura.domain.usecase.SearchWeatherByNameUseCase
import org.koin.dsl.module

val useCaseModule = module {
    single { GetAllCitiesUseCase( get() ) }
    single { AddWeatherCityUseCase( get() ) }
    single { SearchWeatherByNameUseCase( get() ) }
    single { DeleteCityUseCase( get() ) }
    single { GetWeatherByCityUseCase( get() ) }
}
