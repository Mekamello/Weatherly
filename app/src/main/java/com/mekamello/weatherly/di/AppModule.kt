package com.mekamello.weatherly.di

import android.content.Context
import com.mekamello.weatherly.base.RxSchedulers
import com.mekamello.weatherly.base.RxSchedulersImpl
import com.mekamello.weatherly.domain.converters.*
import com.mekamello.weatherly.utils.DateUtils
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton

@Module
class AppModule(
        private val applicationContext: Context
) {
    companion object {
        const val APPLICATION_CONTEXT = "ApplicationContext"
    }

    @Provides
    @Singleton
    @Named(APPLICATION_CONTEXT)
    fun provideApplicationContext(): Context = applicationContext

    @Provides
    @Singleton
    fun provideWeatherConverter() = WeatherConverter()

    @Provides
    @Singleton
    fun provideDailyConverter(weatherConverter: WeatherConverter) =
            DailyConverter(weatherConverter)

    @Provides
    @Singleton
    fun provideCityConverter() = CityConverter()

    @Provides
    @Singleton
    fun provideCityDetailConverter(cityConverter: CityConverter) = ForecastConverter(cityConverter)

    @Provides
    @Singleton
    fun provideCityLightConverter(dateUtils: DateUtils) = CityMainConverter(dateUtils)

    @Provides
    @Singleton
    fun provideDateUtils() = DateUtils()

    @Provides
    @Singleton
    fun provideRxSchedulers(): RxSchedulers = RxSchedulersImpl()

}