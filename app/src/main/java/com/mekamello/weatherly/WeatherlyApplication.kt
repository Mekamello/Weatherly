package com.mekamello.weatherly

import android.app.Application
import com.mekamello.weatherly.di.AppComponent
import com.mekamello.weatherly.di.AppModule
import com.mekamello.weatherly.di.DaggerAppComponent

class WeatherlyApplication : Application() {
    companion object {
        lateinit var component: AppComponent
    }

    override fun onCreate() {
        super.onCreate()
        component = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()
        component.inject(this)
    }
}