package com.mekamello.weatherly.ui.cities

import com.mekamello.weatherly.di.PerActivity
import com.mekamello.weatherly.domain.repositories.*
import com.mekamello.weatherly.domain.usecases.UpdateWeatherUseCase
import com.mekamello.weatherly.domain.usecases.UpdateWeatherUseCaseImpl
import dagger.Binds
import dagger.Module

@Module
abstract class CityListBinds {
    @Binds
    @PerActivity
    abstract fun providePresenter(impl: CityListPresenterImpl): CityListPresenter

    @Binds
    @PerActivity
    abstract fun provideInteractor(impl: CityListInteractorImpl): CityListInteractor

    @Binds
    @PerActivity
    abstract fun provideCityRepository(impl: CityRepositoryImpl): CityRepository

    @Binds
    @PerActivity
    abstract fun provideCityDetailRepository(impl:CityDetailRepositoryImpl): CityDetailRepository

    @Binds
    @PerActivity
    abstract fun provideCityUpdateDateRepository(impl:CityUpdateDateRepositoryImpl): CityUpdateDateRepository

    @Binds
    @PerActivity
    abstract fun provideWeatherInfoRepository(impl:WeatherInfoRepositoryImpl): WeatherInfoRepository

    @Binds
    @PerActivity
    abstract fun provideUpdateWeatherUseCase(impl: UpdateWeatherUseCaseImpl): UpdateWeatherUseCase
}