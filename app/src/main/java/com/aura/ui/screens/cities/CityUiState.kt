package com.aura.ui.screens.cities

import com.aura.R
import com.aura.ui.model.CityUiModel

data class CityUiState(
    val items:List<CityUiModel> = emptyList(),
    val inProgress: Boolean = false,
    val msgRes:Int = R.string.msg_empty
)