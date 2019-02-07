package com.mekamello.weatherly.domain.models

data class CityMain(
    val city: City,
    val temp: Float,
    val weatherId: Int,
    val weatherMain: String,
    val weatherDescription: String
)