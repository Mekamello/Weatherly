package com.mekamello.weatherly.ui.detail

import com.mekamello.weatherly.di.PerActivity
import dagger.Subcomponent

@PerActivity
@Subcomponent(modules = [CityDetailModule::class, CityDetailBinds::class])
interface CityDetailComponent {
    fun inject(activity: CityDetailActivity)
}