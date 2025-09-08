package com.aura.ui.model

data class CityUiModel(
    val id: Long,
    val name: String,
    val country: String,
    val lat: Double,
    val lon: Double
){
    override fun toString(): String = "$name, $country"
}