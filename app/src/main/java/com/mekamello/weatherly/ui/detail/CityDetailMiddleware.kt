package com.mekamello.weatherly.ui.detail

import com.mekamello.weatherly.base.Middleware
import com.mekamello.weatherly.base.RxSchedulers
import com.mekamello.weatherly.domain.models.Forecast
import com.mekamello.weatherly.domain.models.Weather
import com.mekamello.weatherly.domain.repositories.ForecastRepository
import com.mekamello.weatherly.domain.usecases.UpdateWeatherUseCase
import com.mekamello.weatherly.utils.DateUtils
import io.reactivex.Observable
import io.reactivex.rxkotlin.ofType
import io.reactivex.rxkotlin.withLatestFrom
import javax.inject.Inject

class CityDetailMiddleware
@Inject constructor(
    private val schedulers: RxSchedulers,
    private val repository: ForecastRepository,
    private val updateUseCase: UpdateWeatherUseCase,
    private val dateUtils: DateUtils
) : Middleware<CityDetailAction, CityDetailViewState> {
    override fun bind(
        actions: Observable<CityDetailAction>,
        state: Observable<CityDetailViewState>
    ): Observable<CityDetailAction> =
        actions.ofType<CityDetailAction.Refresh>()
            .withLatestFrom(state) { _, currentState -> currentState }
            .flatMap { currentState -> loadContent(currentState) }

    private fun loadContent(state: CityDetailViewState) =
        Observable.merge(getStoredCityDetail(state.cityId), getRemoteCityDetail(state.cityId))
            .map<CityDetailInternalAction> {
                CityDetailInternalAction.Success(
                    it.city,
                    groupByDay(it)
                )
            }
            .onErrorReturn { CityDetailInternalAction.Error(it) }
            .startWith(CityDetailInternalAction.Loading)
            .subscribeOn(schedulers.io())

    private fun getStoredCityDetail(cityId: Int) =
        repository.get(cityId)
            .toObservable()

    private fun getRemoteCityDetail(cityId: Int) =
        updateUseCase.execute(cityId)
            .andThen(getStoredCityDetail(cityId))

    private fun groupByDay(forecast: Forecast) =
        forecast.weathers
            .groupBy { dateUtils.getDayOfWeek(it.getDateInMills()) }
            .map { getDailyItem(it.key, it.value.sortedBy { daily -> daily.date }) }

    private fun getDailyItem(dayOfWeek: String, weathers: List<Weather>) =
        DailyItem(
            date = weathers.first().date,
            weatherId = weathers.first().atmosphere.first().id,
            dayOfWeek = dayOfWeek,
            weatherDescription = weathers.first().atmosphere.first().description,
            min = weathers.getByMinTemp()?.temp?.temp ?: 0F,
            max = weathers.getByMaxTemp()?.temp?.temp ?: 0F
        )

    private fun List<Weather>.getByMaxTemp() =
        maxBy { it.temp.temp }

    private fun List<Weather>.getByMinTemp() =
        minBy { it.temp.temp }

}