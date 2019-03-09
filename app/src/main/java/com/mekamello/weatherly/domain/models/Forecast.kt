package com.mekamello.weatherly.domain.models

data class Forecast(
    val city: City,
    val weathers: List<Weather>
)