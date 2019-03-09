package com.mekamello.weatherly.ui.cities

import com.mekamello.weatherly.base.Action
import com.mekamello.weatherly.domain.models.CityShortWeather

sealed class CityListAction : Action {
    object Refresh : CityListAction()
    data class AddCity(val name: String) : CityListAction()
    data class OpenCityDetail(val cityId: Int) : CityListAction()
}

sealed class CityListInternalAction : CityListAction() {
    data class UpdateCity(val cityId: Int): CityListInternalAction()
    data class OpenCity(val cityId: Int) : CityListInternalAction()
    data class Success(val cities: List<CityShortWeather> = listOf()) : CityListInternalAction()
    data class Error(val throwable: Throwable): CityListInternalAction()
}