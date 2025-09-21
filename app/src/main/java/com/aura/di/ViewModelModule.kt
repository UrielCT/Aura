package com.aura.di

import com.aura.ui.screens.cities.CitiesViewModel
import com.aura.ui.screens.weather.WeatherViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { WeatherViewModel( get(), get(), get(),get(), get(),get(), get()) }
    viewModel { CitiesViewModel( get(), get(), get(), get(), get()) }
}