package com.mekamello.weatherly.ui.detail

import com.mekamello.weatherly.di.PerActivity
import com.mekamello.weatherly.domain.repositories.*
import com.mekamello.weatherly.domain.usecases.UpdateWeatherUseCase
import com.mekamello.weatherly.domain.usecases.UpdateWeatherUseCaseImpl
import com.mekamello.weatherly.ui.resources.WeatherIconResourceProvider
import com.mekamello.weatherly.ui.resources.WeatherIconResourceProviderImpl
import dagger.Binds
import dagger.Module

@Module
interface CityDetailBinds {

    @Binds
    @PerActivity
    fun provideCityDetailInteractor(impl: CityDetailInteractorImpl): CityDetailInteractor

    @Binds
    @PerActivity
    fun provideCityDetailRepository(impl: CityDetailRepositoryImpl): CityDetailRepository

    @Binds
    @PerActivity
    fun provideUpdateWeatherUseCase(impl: UpdateWeatherUseCaseImpl): UpdateWeatherUseCase

    @Binds
    @PerActivity
    fun provideCityUpdateDateRepository(impl: CityUpdateDateRepositoryImpl): CityUpdateDateRepository

    @Binds
    @PerActivity
    fun provideWeatherInfoRepository(impl: WeatherInfoRepositoryImpl): WeatherInfoRepository

    @Binds
    @PerActivity
    fun provideWeatherIconProvider(impl: WeatherIconResourceProviderImpl): WeatherIconResourceProvider
}