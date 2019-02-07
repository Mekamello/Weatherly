package com.mekamello.weatherly.domain.models

data class Daily(
        val date: Long,
        val weather: List<Weather>,
        val temp: Temperature
)