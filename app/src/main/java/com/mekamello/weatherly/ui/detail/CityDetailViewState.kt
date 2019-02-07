package com.mekamello.weatherly.ui.detail

import com.mekamello.weatherly.domain.models.City

sealed class CityDetailViewState {
    object Loading : CityDetailViewState()
    data class Content(val city: City, val dailies: List<DailyItem>) : CityDetailViewState()
    data class Error(val throwable: Throwable) : CityDetailViewState()
}