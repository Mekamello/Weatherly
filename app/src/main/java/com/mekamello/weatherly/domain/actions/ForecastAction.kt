package com.mekamello.weatherly.domain.actions

import com.mekamello.weatherly.base.Action

sealed class ForecastAction : Action {
    data class Get(val cityId: Int) : ForecastAction()
}

sealed class ForecastInternalAction : ForecastAction() {
    data class Success() : ForecastInternalAction()
    data class Failure(val throwable: Throwable) : ForecastInternalAction()
}
