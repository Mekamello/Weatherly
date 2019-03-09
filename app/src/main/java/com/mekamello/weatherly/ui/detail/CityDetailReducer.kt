package com.mekamello.weatherly.ui.detail

import com.mekamello.weatherly.base.Reducer
import javax.inject.Inject

class CityDetailReducer
@Inject constructor(

) : Reducer<CityDetailViewState, CityDetailAction> {
    override fun reduce(state: CityDetailViewState, action: CityDetailAction): CityDetailViewState = when (action) {
        is CityDetailInternalAction.Loading -> state.copy(
            loading = true
        )
        is CityDetailInternalAction.Error -> state.copy(
            loading = false,
            throwable = action.throwable
        )
        is CityDetailInternalAction.Success -> state.copy(
            loading = false,
            dailies = action.dailes,
            city = action.city,
            throwable = null
        )
        else -> state
    }
}