package com.mekamello.weatherly.ui.detail

import android.content.Context
import com.mekamello.weatherly.di.PerActivity
import dagger.Module
import dagger.Provides

@Module
class CityDetailModule(val context: Context, val cityId: Int) {

    @Provides
    @PerActivity
    fun provideCityDetailPresenter(interactor: CityDetailInteractor): CityDetailPresenter =
            CityDetailPresenterImpl(interactor, cityId)
}