package com.mekamello.weatherly.ui.detail

import com.mekamello.weatherly.base.Action
import com.mekamello.weatherly.domain.models.City

sealed class CityDetailAction : Action {
    object Refresh : CityDetailAction()
}

sealed class CityDetailInternalAction : CityDetailAction() {
    class Success(val city: City, val dailes: List<DailyItem>) : CityDetailInternalAction()
    class Error(val throwable: Throwable) : CityDetailInternalAction()
    object Loading : CityDetailInternalAction()
}

