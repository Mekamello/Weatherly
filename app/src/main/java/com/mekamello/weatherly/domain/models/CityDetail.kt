package com.mekamello.weatherly.domain.models

data class CityDetail(
    val city: City,
    val daily: List<Daily>
)