package com.aura.ui.screens.cities

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.aura.R
import com.aura.ui.components.CustomSnackBar
import com.aura.ui.components.DialogInfo
import com.aura.ui.components.ProgressFullScreen
import com.aura.ui.components.TextTitle
import com.aura.ui.model.CityUiModel
import com.aura.ui.theme.AuraTheme
import com.aura.ui.theme.CommonPaddingDefault
import com.aura.ui.theme.CommonPaddingMiddle
import com.aura.ui.theme.CommonPaddingXLarge
import kotlinx.coroutines.flow.MutableStateFlow
import org.koin.androidx.compose.koinViewModel

@Composable
fun CitiesScreen(
    modifier: Modifier,
    vm: ICitiesViewModel = koinViewModel<CitiesViewModel>()
) {
    val uiState by vm.getUiState().collectAsState()
    var openDialog by remember { mutableStateOf(false) }
    var selectedCityEntity by remember { mutableStateOf<CityUiModel?>(null) }

    Box(modifier.fillMaxSize()) {
        Column {
            TextTitle(R.string.cities_title)

            if(uiState.items.isEmpty()){
                Text(
                    text = stringResource(R.string.cities_msg_empty_list),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = CommonPaddingXLarge),
                    textAlign = TextAlign.Center
                )
            }else{
                LazyColumn {
                    items(uiState.items.size){index ->
                        val city = uiState.items[index]
                        ItemCityView(
                            cityEntity = city,
                            onMap = {
                                vm.showMap(city)
                            },
                            onRemove = { ct ->
                                selectedCityEntity = ct
                                openDialog = true
                            }
                        )
                    }
                }
            }
        }

        if (openDialog){
            selectedCityEntity?.let { city ->
                DialogInfo(
                    infoRes = R.string.dialog_msg_warning,
                    titleRes = R.string.dialog_delete_title,
                    confirmRes = R.string.dialog_delete_confirm
                ) { isDeleted ->
                    if (isDeleted) vm.deleteCity(city)
                    openDialog = false
                }
            }
        }

        CustomSnackBar(
            modifier = Modifier.fillMaxSize()
                .padding(bottom = CommonPaddingDefault),
            backgroundColor = Color.White,
            shape = CircleShape,
            msgRes = uiState.msgRes,
            onDismiss = { vm.clearMsg() }
        )

        ProgressFullScreen(visible = uiState.inProgress)
    }

}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CitiesScreenPreview(){
    AuraTheme {
        CitiesScreen(Modifier.padding(top = CommonPaddingMiddle),
            vm = CitiesVmPreview())
    }
}

private class CitiesVmPreview : ICitiesViewModel{
    override fun getUiState() = MutableStateFlow(CityUiState( emptyList() ))
    override fun showMap(cityEntity: CityUiModel) {}
    override fun clearMsg() {}
    override fun deleteCity(cityEntity: CityUiModel) {}
}