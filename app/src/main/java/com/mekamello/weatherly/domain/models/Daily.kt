package com.mekamello.weatherly.domain.models

data class Daily(
        val date: Long,
        val weather: List<Weather>,
        val temp: Temperature
) {
        //TODO Move calculating in converter
        fun getDateInMills() = date * 1000
}