package com.mekamello.weatherly.ui.cities

import com.mekamello.weatherly.domain.models.CityMain

sealed class CityListViewState {
    object Loading : CityListViewState()
    object ShowDialog: CityListViewState()
    data class ShowDetail(
        val id: Int
    ): CityListViewState()
    data class Content(
        val cities: List<CityMain> = listOf()
    ) : CityListViewState()
    data class Error(
        val message: String,
        val throwable: Throwable
    ): CityListViewState()
}