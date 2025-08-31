package com.aura.ui.screens.cities.di

import com.aura.ui.screens.cities.CitiesViewModel
import com.aura.ui.screens.cities.LocalDatabase
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val citiesModule = module {
    single { LocalDatabase( get(), get() ) }
    viewModel { CitiesViewModel( get(), get() ) }
}