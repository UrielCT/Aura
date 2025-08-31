package com.aura.ui.di

import com.aura.ui.utils.FormatUtils
import com.aura.ui.utils.IntentUtils
import com.aura.ui.utils.NetworkUtils
import org.koin.dsl.module

val utilsModule = module {
    single { FormatUtils() }
    single { NetworkUtils( get() ) }
    single { IntentUtils( get() ) }
}