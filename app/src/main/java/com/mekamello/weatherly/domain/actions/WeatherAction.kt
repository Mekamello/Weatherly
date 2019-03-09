package com.mekamello.weatherly.domain.actions

import com.mekamello.weatherly.base.Action
import com.mekamello.weatherly.domain.models.Forecast

sealed class WeatherAction : Action {
    data class Get(val cityName: String) : WeatherAction()
}

sealed class WeatherInternalAction() : WeatherAction() {
    data class Success(val forecast: Forecast) : WeatherInternalAction()
    data class Failure(val throwable: Throwable) : WeatherInternalAction()
}