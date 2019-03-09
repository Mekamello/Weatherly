package com.mekamello.weatherly.ui.detail

import com.mekamello.weatherly.domain.models.City
import com.mekamello.weatherly.ui.detail.DailyItem

data class CityDetailViewState(
    val cityId: Int,
    val loading: Boolean = false,
    val city: City? = null,
    val dailies: List<DailyItem> = listOf(),
    val throwable: Throwable? = null
)