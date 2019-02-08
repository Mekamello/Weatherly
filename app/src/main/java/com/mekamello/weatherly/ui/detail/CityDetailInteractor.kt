package com.mekamello.weatherly.ui.detail

import com.mekamello.weatherly.domain.models.CityDetail
import com.mekamello.weatherly.domain.models.Daily
import com.mekamello.weatherly.domain.repositories.CityDetailRepository
import com.mekamello.weatherly.domain.usecases.UpdateWeatherUseCase
import com.mekamello.weatherly.utils.DateUtils
import io.reactivex.Observable
import javax.inject.Inject

interface CityDetailInteractor {
    fun getDetail(cityId: Int): Observable<CityDetailViewState>
}

class CityDetailInteractorImpl
@Inject constructor(
    private val updateWeatherUseCase: UpdateWeatherUseCase,
    private val cityDetailRepository: CityDetailRepository,
    private val dateUtils: DateUtils
) : CityDetailInteractor {
    override fun getDetail(cityId: Int): Observable<CityDetailViewState> =
        Observable.merge(getStoredCityDetail(cityId), getRemoteCityDetail(cityId))
            .map { CityDetailViewState.Content(it.city, groupByDay(it)) }
            .cast(CityDetailViewState::class.java)
            .onErrorReturn { CityDetailViewState.Error(it) }
            .startWith(CityDetailViewState.Loading)

    private fun getStoredCityDetail(cityId: Int) =
        cityDetailRepository.get(cityId)
            .toObservable()

    private fun getRemoteCityDetail(cityId: Int) =
        updateWeatherUseCase.execute(cityId)
            .andThen(getStoredCityDetail(cityId))

    private fun groupByDay(cityDetail: CityDetail) =
        cityDetail.daily
            .groupBy { dateUtils.getDayOfWeek(it.getDateInMills()) }
            .map { getDailyItem(it.key, it.value.sortedBy { daily -> daily.date }) }

    private fun getDailyItem(dayOfWeek: String, dailies: List<Daily>) =
        DailyItem(
            date = dailies.first().date,
            dayOfWeek = dayOfWeek,
            weatherDescription = dailies.first().weather.first().description,
            min = dailies.getByMinTemp()?.temp?.temp ?: 0F,
            max =  dailies.getByMaxTemp()?.temp?.temp ?: 0F
        )

    private fun List<Daily>.getByMaxTemp() =
            maxBy { it.temp.temp }

    private fun List<Daily>.getByMinTemp() =
            minBy { it.temp.temp }
}