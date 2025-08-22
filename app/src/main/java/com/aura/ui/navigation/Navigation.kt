package com.aura.ui.navigation

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
    startDestination: Destination,
    modifier: Modifier=Modifier
){
    NavHost(
        navController = navController,
        startDestination = startDestination.route
    ){
        Destination.entries.forEach{ destination ->
            composable(destination.route){
                when(destination){
                    Destination.WEATHER -> WeatherScreen(modifier)
                    Destination.CITIES -> CitiesScreen(modifier)
                }
            }
        }

    }
}