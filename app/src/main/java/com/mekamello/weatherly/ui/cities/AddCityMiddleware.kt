package com.mekamello.weatherly.ui.cities

import com.mekamello.weatherly.base.Middleware
import com.mekamello.weatherly.base.RxSchedulers
import com.mekamello.weatherly.domain.api.WeatherApi
import com.mekamello.weatherly.domain.converters.WeatherConverter
import com.mekamello.weatherly.domain.models.City
import com.mekamello.weatherly.domain.repositories.CityRepository
import io.reactivex.Observable
import io.reactivex.rxkotlin.ofType
import javax.inject.Inject

class AddCityMiddleware
@Inject constructor(
    private val schedulers: RxSchedulers,
    private val cityRepository: CityRepository,
    private val weatherApi: WeatherApi,
    private val weatherConverter: WeatherConverter
) : Middleware<CityListAction, CityListViewState> {
    override fun bind(
        actions: Observable<CityListAction>,
        state: Observable<CityListViewState>
    ): Observable<CityListAction> =
        actions.ofType<CityListAction.AddCity>()
            .observeOn(schedulers.io())
            .flatMap { execute(it.name) }

    private fun execute(name: String): Observable<CityListInternalAction> =
        cityRepository.getAll()
            .map { cities -> createIfNeed(cities, name)}

    private fun createIfNeed(cities: List<City>, byName: String) =
        cities.filter { it.name == byName }
            .map { Observable.just(CI) }
            .ifEmpty { getWeather(request(byName)) }

    protected fun getWeather(request: Map<String, String>) =
        weatherApi.getWeather(request)
            .map { weatherConverter.convert(it) }

    private fun request(cityName: String): Map<String, String> =
        mapOf(
            Pair("q", cityName),
            Pair("APPID", WeatherApi.APP_KEY),
            Pair("units", "metric")
        )

}