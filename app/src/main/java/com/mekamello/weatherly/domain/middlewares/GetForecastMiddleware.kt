package com.mekamello.weatherly.domain.middlewares

import com.mekamello.weatherly.base.Middleware
import com.mekamello.weatherly.base.RxSchedulers
import com.mekamello.weatherly.domain.actions.ForecastAction
import com.mekamello.weatherly.domain.actions.ForecastInternalAction
import com.mekamello.weatherly.domain.actions.WeatherAction
import com.mekamello.weatherly.domain.actions.WeatherInternalAction
import com.mekamello.weatherly.domain.api.WeatherApi
import com.mekamello.weatherly.domain.converters.DailyConverter
import com.mekamello.weatherly.domain.repositories.WeatherRepository
import io.reactivex.Observable
import io.reactivex.rxkotlin.ofType

abstract class GetForecastMiddleware<S>(
    private val schedulers: RxSchedulers,
    private val weatherApi: WeatherApi,
    private val weatherRepository: WeatherRepository,
    private val dailyConverter: DailyConverter
) : Middleware<ForecastAction, S> {
    override fun bind(
        actions: Observable<ForecastAction>,
        state: Observable<S>
    ): Observable<ForecastAction> =
        actions.ofType<ForecastAction.Get>()
            .observeOn(schedulers.io())
            .flatMap { execute(it.cityId) }

    private fun execute(cityId: Int) =
        weatherApi.getForecast(cityId = cityId)
            .map { dailyConverter.convert(it) }
            .map<ForecastAction> { ForecastInternalAction.Success(it) }
            .onErrorReturn { ForecastInternalAction.Failure(it) }
}