package com.aura.ui.screens.weather

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.aura.R
import com.aura.ui.components.CoilImage
import com.aura.ui.components.ProgressFullScreen
import com.aura.ui.components.TextTitle
import com.aura.ui.models.WeatherCity
import com.aura.ui.theme.AuraTheme
import com.aura.ui.theme.CommonPaddingDefault
import com.aura.ui.theme.CommonPaddingLarge
import com.aura.ui.theme.CommonPaddingMin
import com.aura.ui.theme.CommonPaddingXLarge
import com.aura.ui.theme.Typography
import org.koin.androidx.compose.koinViewModel

@Composable
fun WeatherScreen(
    modifier: Modifier,
    vm:WeatherViewModel = koinViewModel()
){
    val uiState by vm.uiState.collectAsState()

    Box(modifier.fillMaxSize()){
        Column (
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(CommonPaddingDefault)
        ){
            TextTitle(R.string.weather_title)
            WeatherInfoView(uiState.data)
            SearchView { name ->
                vm.searchWeather(name)
            }
        }
        ProgressFullScreen(visible = uiState.inProgress)
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
                .size(CommonPaddingXLarge)
                .padding(top = CommonPaddingMin),
            shape = RectangleShape
        )

        Text(text = weatherCity.description,
            style = Typography.headlineSmall,
            textAlign = TextAlign.Center)

        Text(text = if(weatherCity.name.isEmpty()) "" else "${weatherCity.wind_kph} km/h",
            style = Typography.bodyLarge)
    }
}

@Composable
private fun SearchView(onSearch: (String) -> Unit){
    var cityValue by remember { mutableStateOf("") }
    Row(verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(CommonPaddingMin)
    ){
        OutlinedTextField(value = cityValue,
            onValueChange = {cityValue = it},
            label= { Text(stringResource(R.string.cities_hint_search_city)) })
        FilledIconButton(onClick = {onSearch(cityValue)}) {
            Icon(imageVector = Icons.Default.Search, contentDescription = null)
        }
    }
}



@Preview(showBackground = true)
@Composable
private fun SearchPreview(){
    AuraTheme {
        SearchView {  }
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