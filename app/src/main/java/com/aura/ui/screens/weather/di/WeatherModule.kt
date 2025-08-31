package com.aura.ui.screens.weather.di

import com.aura.domain.DataSource
import com.aura.ui.RemoteDatabase
import com.aura.ui.screens.weather.WeatherViewModel
import com.aura.ui.utils.LocalDatabase
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val weatherModule = module {
    single { RemoteDatabase( get() ) }
    single { LocalDatabase( get(), get(), get(), get()) }
    single { DataSource( get(), get(), get(), get()) }
    viewModel { WeatherViewModel(get()) }
}