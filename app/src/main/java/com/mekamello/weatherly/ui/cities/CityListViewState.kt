package com.mekamello.weatherly.ui.cities

import com.mekamello.weatherly.domain.models.CityShortWeather

data class CityListViewState(
    val loading: Boolean = false,
    val cities: List<CityShortWeather> = listOf(),
    val openDetail: OpenDetail? = null,
    val throwable: Throwable? = null
) {
    data class OpenDetail(val cityId: Int)
}