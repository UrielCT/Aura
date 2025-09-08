package com.aura

import com.aura.data.datasource.remote.RemoteDatabaseTest
import com.aura.domain.repository.WeatherRepositoryImplTest
import com.aura.domain.usecase.UseCaseTest
import com.aura.ui.screens.cities.CitiesViewModelTest
import com.aura.ui.screens.weather.WeatherViewModelTest
import com.aura.ui.utils.FormatUtilsTest
import org.junit.runner.RunWith
import org.junit.runners.Suite
import org.junit.runners.Suite.SuiteClasses

@RunWith(Suite::class)
@SuiteClasses(
    FormatUtilsTest::class,
    RemoteDatabaseTest::class,
    WeatherRepositoryImplTest::class,
    UseCaseTest::class,
    WeatherViewModelTest::class,
    CitiesViewModelTest::class
)
class AllTests { }