package com.mekamello.weatherly.ui.cities

import com.mekamello.weatherly.base.Reducer

class CityListReducer : Reducer<CityListViewState, CityListAction> {
    override fun reduce(state: CityListViewState, action: CityListAction): CityListViewState = when(action) {
        is CityListAction.Refresh -> state.copy(loading = true)
        is CityListInternalAction.Success -> state.copy(
            loading = false,
            cities = action.cities,
            openDetail = null
        )
        is CityListInternalAction.OpenCity -> state.copy(
            loading = false,
            openDetail = CityListViewState.OpenDetail(action.cityId)
        )
        is CityListInternalAction.Error -> state.copy(
            loading = false,
            throwable = action.throwable,
            openDetail = null
        )
        else -> state
    }
}