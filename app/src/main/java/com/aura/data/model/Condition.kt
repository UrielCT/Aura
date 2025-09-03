package com.aura.data.model

data class Condition(
    val text:String= "",
    val icon:String= "",
){
    val iconHttps get() = "https:$icon"
}
