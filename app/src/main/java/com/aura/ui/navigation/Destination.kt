package com.aura.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationCity
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.ui.graphics.vector.ImageVector
import com.aura.R
import com.aura.ui.utils.Constants

enum class Destination(
    val route:String,
    val labelRes:Int,
    val icon:ImageVector
) {
    WEATHER(route = Constants.NAV_WEATHER, labelRes = R.string.weather_title, Icons.Default.WbSunny),
    CITIES(route = Constants.NAV_CITIES, labelRes = R.string.cities_title, Icons.Default.LocationCity),
}