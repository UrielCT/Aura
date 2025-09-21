package com.aura.ui.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.aura.ui.screens.cities.CitiesScreen
import com.aura.ui.screens.weather.WeatherScreen

@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier=Modifier
){
    NavHost(
        navController = navController,
        startDestination =  Destination.WEATHER.route,
        modifier = modifier
    ){

        composable(Destination.WEATHER.route) {
            WeatherScreen(modifier = Modifier.fillMaxSize())
        }
        composable(Destination.CITIES.route) {
            CitiesScreen(modifier = Modifier.fillMaxSize())
        }

    }
}