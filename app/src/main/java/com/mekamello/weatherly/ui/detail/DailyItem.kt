package com.mekamello.weatherly.ui.detail

data class DailyItem(
    val date: Long,
    val dayOfWeek: String,
    val weatherDescription: String,
    val max: Float,
    val min: Float
)