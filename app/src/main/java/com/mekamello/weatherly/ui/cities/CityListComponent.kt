package com.mekamello.weatherly.ui.cities

import com.mekamello.weatherly.di.PerActivity
import dagger.Subcomponent

@PerActivity
@Subcomponent(modules = [CityListModule::class, CityListBinds::class])
interface CityListComponent {
    fun inject(activity: CityListActivity)
}