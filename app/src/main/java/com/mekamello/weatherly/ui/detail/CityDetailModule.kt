package com.mekamello.weatherly.ui.detail

import android.content.Context
import com.mekamello.weatherly.base.Middleware
import com.mekamello.weatherly.base.Reducer
import com.mekamello.weatherly.base.RxSchedulers
import com.mekamello.weatherly.base.Store
import com.mekamello.weatherly.di.PerActivity
import com.mekamello.weatherly.ui.resources.WeatherIconResourceProvider
import dagger.Module
import dagger.Provides

@Module
class CityDetailModule(val context: Context, val cityId: Int) {

    @Provides
    @PerActivity
    fun provideCityDetailStore(
        schedulers: RxSchedulers,
        reducer: Reducer<CityDetailViewState, CityDetailAction>,
        middleware: Middleware<CityDetailAction, CityDetailViewState>
    ): Store<CityDetailAction, CityDetailViewState> =
        Store(
            reducer = reducer,
            middlewares = listOf(middleware),
            schedulers = schedulers,
            initialState = CityDetailViewState(cityId = cityId)
        )

    @Provides
    @PerActivity
    fun provideCityDetailAdapter(iconResourceProvider: WeatherIconResourceProvider): CityDetailAdapter =
        CityDetailAdapter(iconResourceProvider)
}