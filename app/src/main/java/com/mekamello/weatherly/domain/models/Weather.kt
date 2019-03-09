package com.mekamello.weatherly.domain.models

data class Weather(
        val date: Long,
        val atmosphere: List<Atmosphere>,
        val temp: Temperature
) {
        //TODO Move calculating in converter
        fun getDateInMills() = date * 1000
}