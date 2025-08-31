package com.aura.ui.screens.cities

import com.aura.R
import com.aura.ui.models.City

data class CityUiState(
    val items:List<City> = emptyList(),
    val inProgress: Boolean = false,
    val msgRes:Int = R.string.msg_empty
)