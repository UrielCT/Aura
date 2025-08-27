package com.aura.ui.screens.weather

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.aura.R
import com.aura.ui.components.CoilImage
import com.aura.ui.components.TextTitle
import com.aura.ui.models.WeatherCity
import com.aura.ui.theme.AuraTheme
import com.aura.ui.theme.CommonPaddingDefault
import com.aura.ui.theme.CommonPaddingLarge
import com.aura.ui.theme.CommonPaddingMin
import com.aura.ui.theme.Typography

@Composable
fun WeatherScreen(
    modifier: Modifier,
    vm:WeatherViewModel= viewModel()
){
    val uiState by vm.uiState.collectAsState()

    Box(modifier.fillMaxSize()){
        Column (
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(CommonPaddingDefault)
        ){
            TextTitle(R.string.weather_title)
            WeatherInfoView(uiState.data)
        }
    }
}



@Composable
private fun WeatherInfoView(weatherCity: WeatherCity){
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "${weatherCity.temp_c.toInt()}°",
            style = Typography.displayLarge)

        Text(text = weatherCity.name,
            style = Typography.headlineLarge)

        Text(text = weatherCity.country,
            style = Typography.bodyLarge)

        CoilImage(
            url = weatherCity.iconHttps,
            modifier = Modifier
                .size(CommonPaddingLarge)
                .padding(top = CommonPaddingMin)
        )

        Text(text = weatherCity.description,
            style = Typography.headlineSmall,
            textAlign = TextAlign.Center)

        Text(text = if(weatherCity.name.isEmpty()) "" else "${weatherCity.wind_kph} km/h",
            style = Typography.bodyLarge)
    }
}



@Preview(showBackground = true)
@Composable
private fun WeatherScreenPreview(){
    AuraTheme {
        WeatherScreen(Modifier)
    }
}

@Preview(showBackground = true)
@Composable
private fun WeatherInfoPreview(){

    val weatherCityPreview= WeatherCity(
        31f,"Vientos fuertes", 22.5f, "","Lima","Peru"
    )

    AuraTheme {
        WeatherInfoView(weatherCityPreview)
    }
}