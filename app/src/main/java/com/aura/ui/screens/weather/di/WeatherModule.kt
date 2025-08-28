package com.aura.ui.screens.weather.di

import com.aura.ui.RemoteDatabase
import com.aura.ui.screens.weather.WeatherViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val weatherModule = module {
    single { RemoteDatabase( get(), get() ) }
    viewModel { WeatherViewModel(get()) }
}