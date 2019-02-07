package com.mekamello.weatherly.ui.cities

import com.mekamello.weatherly.domain.api.WeatherApi
import com.mekamello.weatherly.domain.converters.CityMainConverter
import com.mekamello.weatherly.domain.converters.WeatherConverter
import com.mekamello.weatherly.domain.models.City
import com.mekamello.weatherly.domain.models.CityDetail
import com.mekamello.weatherly.domain.models.CityMain
import com.mekamello.weatherly.domain.repositories.CityDetailRepository
import com.mekamello.weatherly.domain.repositories.CityRepository
import com.mekamello.weatherly.domain.usecases.UpdateWeatherUseCase
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject

interface CityListInteractor {
    fun addCity(name: String): Observable<CityListViewState>
    fun getCityList(): Observable<CityListViewState>
}

class CityListInteractorImpl
@Inject constructor(
    private val weatherApi: WeatherApi,
    private val updateWeatherUseCase: UpdateWeatherUseCase,
    private val cityRepository: CityRepository,
    private val cityDetailRepository: CityDetailRepository,
    private val weatherConverter: WeatherConverter,
    private val cityMainConverter: CityMainConverter
) : CityListInteractor {
    override fun addCity(name: String): Observable<CityListViewState> =
        cityRepository.getAll()
            .map { cities -> cities.filter { it.name == name } }
            .createOrUpdate(name)
            .startWith(CityListViewState.Loading)

    override fun getCityList(): Observable<CityListViewState> =
        Observable.merge(getStoredCityLightList(), getRemoteCityLightList())
            .map { CityListViewState.Content(it) }
            .cast(CityListViewState::class.java)
            .onErrorReturn { CityListViewState.Error(it.message ?: "", it) }
            .startWith(CityListViewState.Loading)

    private fun Single<List<City>>.createOrUpdate(name: String) =
        flatMapObservable {
            if (it.isNotEmpty()) {
                updateCityWeather(it.first())
            } else {
                createNewCity(name)
            }
        }

    private fun updateCityWeather(city: City) =
        updateWeatherUseCase.execute(city.id)
            .andThen(getCityList())

    private fun createNewCity(name: String) =
        getWeather(name)
            .addAndGetAll()
            .map { CityListViewState.Content(it) }
            .cast(CityListViewState::class.java)
            .onErrorReturn { CityListViewState.Error(it.message ?: "", it) }

    private fun Observable<CityDetail>.addAndGetAll(): Observable<List<CityMain>> =
        flatMapCompletable { addWeather(it) }
            .andThen(getStoredCityLightList())

    private fun addWeather(cityDetail: CityDetail) =
        cityRepository.add(cityDetail.city)
            .andThen(cityDetailRepository.add(cityDetail))

    private fun Single<List<City>>.updateWeatherByEachCity() =
        flatMapObservable { Observable.fromIterable(it) }
            .flatMapCompletable { updateWeatherUseCase.execute(it.id) }
            .andThen(getStoredCityLightList())

    private fun getStoredCityLightList() =
        cityDetailRepository.getAll()
            .map { list -> list.map { cityMainConverter.convert(it) } }
            .toObservable()

    private fun getRemoteCityLightList() =
        cityRepository.getAll()
            .updateWeatherByEachCity()

    private fun getWeather(cityName: String) =
        weatherApi.getWeather(request(cityName))
            .map { weatherConverter.convert(it) }

    private fun request(cityName: String): Map<String, String> =
        mapOf(
            Pair("q", cityName),
            Pair("APPID", WeatherApi.APP_KEY),
            Pair("units", "metric")
        )
}