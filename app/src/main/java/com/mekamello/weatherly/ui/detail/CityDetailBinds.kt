package com.mekamello.weatherly.ui.detail

import com.mekamello.weatherly.base.Middleware
import com.mekamello.weatherly.base.Reducer
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
    fun provideCityDetailMiddleware(impl: CityDetailMiddleware): Middleware<CityDetailAction, CityDetailViewState>

    @Binds
    @PerActivity
    fun provideCityDetailReducer(impl: CityDetailReducer): Reducer<CityDetailViewState, CityDetailAction>

    @Binds
    @PerActivity
    fun provideCityDetailRepository(impl: ForecastRepositoryImpl): ForecastRepository

    @Binds
    @PerActivity
    fun provideUpdateWeatherUseCase(impl: UpdateWeatherUseCaseImpl): UpdateWeatherUseCase

    @Binds
    @PerActivity
    fun provideCityUpdateDateRepository(impl: CityUpdateDateRepositoryImpl): CityUpdateDateRepository

    @Binds
    @PerActivity
    fun provideWeatherInfoRepository(impl: WeatherRepositoryImpl): WeatherRepository

    @Binds
    @PerActivity
    fun provideWeatherIconProvider(impl: WeatherIconResourceProviderImpl): WeatherIconResourceProvider
}