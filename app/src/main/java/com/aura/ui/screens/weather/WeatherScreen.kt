package com.aura.ui.screens.weather

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CloudDownload
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import com.aura.R
import com.aura.ui.components.AuraDropdownMenu
import com.aura.ui.components.CoilImage
import com.aura.ui.components.CustomSnackBar
import com.aura.ui.components.ProgressFullScreen
import com.aura.ui.components.TextTitle
import com.aura.ui.model.CityUiModel
import com.aura.ui.model.WeatherCityUiModel
import com.aura.ui.theme.CommonPaddingDefault
import com.aura.ui.theme.CommonPaddingMin
import com.aura.ui.theme.CommonPaddingXLarge
import com.aura.ui.theme.MessageVerticalSpace
import com.aura.ui.theme.Typography
import org.koin.androidx.compose.koinViewModel

@Composable
fun WeatherScreen(
    modifier: Modifier,
    vm:WeatherViewModel = koinViewModel()
){
    val uiState by vm.uiState.collectAsState()
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    LaunchedEffect(Unit) {
        focusManager.clearFocus(force = true)
        keyboardController?.hide()
    }

    Box(
        modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures {
                    focusManager.clearFocus(force = true)
                    keyboardController?.hide()
                }
            }
    ){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .imePadding()
                .padding(CommonPaddingDefault),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ){
            TextTitle(R.string.weather_title)

            WeatherInfoView(uiState.data)

            ActionsView(
                uiState= uiState,
                onSelect = { cityUi -> vm.getWeatherByCity(cityUi) },
                onSave = {
                    focusManager.clearFocus(force = true)
                    keyboardController?.hide()
                    uiState.data?.let { vm.saveWeatherCity(it) }
                }
            )

            CustomSnackBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(MessageVerticalSpace),
                msgRes = uiState.msgRes,
                onDismiss = {
                    vm.clearMsg()
                },
                textColor = MaterialTheme.colorScheme.primary
            )

            SearchView { name ->
                keyboardController?.hide()
                focusManager.clearFocus(force = true)
                vm.searchWeather(name)
            }
        }

        ProgressFullScreen(visible = uiState.inProgress)
    }
}

@Composable
private fun WeatherInfoView(
    weatherCity: WeatherCityUiModel?,
    modifier: Modifier = Modifier
){
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = weatherCity?.tempText ?: "",
            style = Typography.displayLarge)

        Text(text = weatherCity?.name ?: "",
            style = Typography.headlineLarge)

        //country
        Text(text = weatherCity?.country ?: "",
            style = Typography.bodyLarge)

        weatherCity?.let {
            CoilImage(
                url = it.iconUrl,
                modifier = Modifier
                    .size(CommonPaddingXLarge)
                    .padding(top = CommonPaddingMin),
                shape = RectangleShape
            )
        }

        Text(text = weatherCity?.description ?: "",
            style = Typography.headlineSmall,
            textAlign = TextAlign.Center)

        Text(text = if(weatherCity!!.name.isEmpty()) "" else weatherCity.windText,
            style = Typography.bodyLarge)
    }
}

@Composable
private fun SearchView(onSearch: (String) -> Unit){
    var cityValue by remember { mutableStateOf("") }

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(CommonPaddingMin)
    ){
        OutlinedTextField(
            value = cityValue,
            onValueChange = {cityValue = it},
            label= { Text(stringResource(R.string.cities_hint_search_city)) },
            singleLine = true,
            maxLines = 1,
            modifier = Modifier
                .weight(1f),
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(
                onSearch = { onSearch(cityValue) }
            ),
        )
        FilledIconButton( onClick = { onSearch(cityValue) }
        ) {
            Icon(imageVector = Icons.Default.Search, contentDescription = null)
        }
    }
}

@Composable
private fun ActionsView(
    uiState: WeatherUiState,
    onSelect:(CityUiModel) -> Unit,
    onSave:()  -> Unit
){
    Row(horizontalArrangement = Arrangement.spacedBy(CommonPaddingMin)) {
        AuraDropdownMenu(
            items = uiState.items,
            labelRes = R.string.cities_city,
            onSelect = { city -> onSelect(city) }
        )

        OutlinedIconButton(
            onClick = { onSave() },
            enabled = uiState.data!!.name.isNotBlank(),
            colors = IconButtonDefaults.iconButtonColors(
                contentColor = MaterialTheme.colorScheme.primary
            )
        ) {
            Icon(Icons.Default.CloudDownload, contentDescription = null)
        }
    }
}