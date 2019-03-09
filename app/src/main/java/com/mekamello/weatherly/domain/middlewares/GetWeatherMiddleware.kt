package com.mekamello.weatherly.domain.middlewares

import com.mekamello.weatherly.base.Middleware
import com.mekamello.weatherly.base.RxSchedulers
import com.mekamello.weatherly.domain.actions.WeatherAction
import com.mekamello.weatherly.domain.actions.WeatherInternalAction
import com.mekamello.weatherly.domain.api.WeatherApi
import com.mekamello.weatherly.domain.converters.WeatherConverter
import com.mekamello.weatherly.domain.repositories.CityRepository
import com.mekamello.weatherly.domain.repositories.WeatherRepository
import io.reactivex.Observable
import io.reactivex.rxkotlin.ofType

class GetWeatherMiddleware<S>(
    private val schedulers: RxSchedulers,
    private val weatherApi: WeatherApi,
    private val weatherRepository: WeatherRepository,
    private val cityRepository: CityRepository,
    private val weatherConverter: WeatherConverter
) : Middleware<WeatherAction, S> {
    override fun bind(
        actions: Observable<WeatherAction>,
        state: Observable<S>
    ): Observable<WeatherAction> =
        actions.ofType<WeatherAction.Get>()
            .observeOn(schedulers.io())
            .flatMap { execute(it.cityName) }

    private fun execute(cityName: String) =
        weatherApi.getWeather(cityName = cityName)
            .map { weatherConverter.convert(it) }
            .map<WeatherAction> { WeatherInternalAction.Success(it) }
            .onErrorReturn { WeatherInternalAction.Failure(it) }
}