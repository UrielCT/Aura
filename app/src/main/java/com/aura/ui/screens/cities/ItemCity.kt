package com.aura.ui.screens.cities

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowOutward
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.aura.ui.model.CityUiModel
import com.aura.ui.theme.CommonPaddingListItemVertical
import com.aura.ui.theme.CommonPaddingMin
import com.aura.ui.theme.Typography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemCityView(
    cityEntity: CityUiModel,
    onMap: (CityUiModel) -> Unit,
    onRemove:(CityUiModel) -> Unit
){
    val swipeToDismissBoxState = rememberSwipeToDismissBoxState(
        confirmValueChange = {
            if(it == SwipeToDismissBoxValue.EndToStart){
                onRemove(cityEntity)
            }
            it != SwipeToDismissBoxValue.EndToStart
        }
    )

    SwipeToDismissBox(
        state = swipeToDismissBoxState,
        modifier = Modifier.fillMaxSize(),
        enableDismissFromStartToEnd = false,
        backgroundContent = {
            when(swipeToDismissBoxState.dismissDirection){
                SwipeToDismissBoxValue.EndToStart -> {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.Red)
                            .wrapContentSize(Alignment.CenterEnd)
                            .padding(CommonPaddingListItemVertical),
                        tint = Color.White
                    )
                }
                else -> {}
            }
        }
    ) {
        Card(modifier = Modifier.padding(CommonPaddingMin)) {
            Row {
                Text(
                    text = cityEntity.toString(),
                    modifier = Modifier
                        .weight(1f)
                        .padding(CommonPaddingMin),
                    style = Typography.headlineSmall
                )
                IconButton(onClick = { onMap(cityEntity) }) {
                    Icon(Icons.Default.ArrowOutward, contentDescription = null)
                }
            }
        }
    }


}