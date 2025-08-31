package com.aura.ui.utils


// https://api.weatherapi.com/v1/current.json?key=60d7d18295d04fae818182508252208&q=London&aqi=no
object Constants {
    //Navigation
    const val NAV_WEATHER = "nav_weather"
    const val NAV_CITIES = "nav_cities"

    //Retrofit
    const val BASE_URL = "https://api.weatherapi.com"
    const val PATH_V1 = "/v1/current.json"

    const val PARAM_KEY = "key"
    const val PARAM_QUERY = "q"
    const val PARAM_LANGUAGE = "lang"

    const val API_KEY = "60d7d18295d04fae818182508252208"
    const val LANG_ES = "es"

    //components
    const val DURATION_SHORT = 3000L
    const val DURATION_LONG = 8000L

    //Room
    const val DB_NAME = "db_aura"
    const val DB_INIT_VERSION = 1
    // E = Entity
    const val E_CITY = "city_entity"
    const val E_WEATHER = "weather_entity"
    // P = Property
    const val P_NAME = "name"
    const val P_COUNTRY = "country"
    const val P_CITY_ID = "cityId"

}