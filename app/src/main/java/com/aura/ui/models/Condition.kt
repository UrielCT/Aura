package com.aura.ui.models

data class Condition(
    val text:String= "",
    val icon:String= "",
){
    val iconHttps get() = "https:$icon"
}
