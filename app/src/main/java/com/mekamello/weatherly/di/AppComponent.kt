package com.mekamello.weatherly.di

import com.mekamello.weatherly.WeatherlyApplication
import com.mekamello.weatherly.ui.cities.CityListComponent
import com.mekamello.weatherly.ui.cities.CityListModule
import com.mekamello.weatherly.ui.detail.CityDetailComponent
import com.mekamello.weatherly.ui.detail.CityDetailModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, RetrofitModule::class, DatabaseModule::class])
interface AppComponent {
    fun inject(application: WeatherlyApplication)
    fun plus(module: CityListModule): CityListComponent
    fun plus(module: CityDetailModule): CityDetailComponent
}