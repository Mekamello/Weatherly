package com.mekamello.weatherly.domain.usecases

import com.mekamello.weatherly.domain.api.WeatherApi
import com.mekamello.weatherly.domain.converters.DailyConverter
import com.mekamello.weatherly.domain.models.CityDetail
import com.mekamello.weatherly.domain.models.CityUpdateDate
import com.mekamello.weatherly.domain.repositories.CityDetailRepository
import com.mekamello.weatherly.domain.repositories.CityUpdateDateRepository
import com.mekamello.weatherly.domain.repositories.WeatherInfoRepository
import com.mekamello.weatherly.utils.DateUtils
import io.reactivex.Completable
import javax.inject.Inject

interface UpdateWeatherUseCase {
    fun execute(cityId: Int): Completable
}

class UpdateWeatherUseCaseImpl
@Inject constructor(
    private val weatherApi: WeatherApi,
    private val weatherInfoRepository: WeatherInfoRepository,
    private val cityDetailRepository: CityDetailRepository,
    private val cityUpdateDateRepository: CityUpdateDateRepository,
    private val dailyConverter: DailyConverter,
    private val dateUtils: DateUtils
) : UpdateWeatherUseCase {
    override fun execute(cityId: Int): Completable =
        cityDetailRepository.get(cityId)
            .flatMapCompletable { updateIfNeed(it) }

    private fun updateIfNeed(cityDetail: CityDetail) =
        if (cityDetail.daily.size <= 1) {
            update(cityDetail.city.id)
        } else {
            cityUpdateDateRepository.get(cityDetail.city.id)
                .flatMapCompletable { updateIfNeed(it) }
        }

    private fun updateIfNeed(cityUpdateDate: CityUpdateDate) =
        if (dateUtils.isPastTenMinutes(cityUpdateDate.date)) {
            weatherInfoRepository.deleteBy(cityUpdateDate.cityId)
                .andThen(update(cityUpdateDate.cityId))
        } else {
            Completable.complete()
        }

    private fun update(cityId: Int) =
        weatherApi.getDaily(request(cityId))
            .map { dailyConverter.convert(it) }
            .flatMapCompletable { addUpdatedWeather(it) }

    private fun addUpdatedWeather(cityDetail: CityDetail) =
        cityDetailRepository.add(cityDetail)
            .andThen(cityUpdateDateRepository.add(cityDetail.city.id, dateUtils.getCurrentTimeInMills()))

    private fun request(cityId: Int): Map<String, String> =
        mapOf(
            Pair("id", cityId.toString()),
            Pair("APPID", WeatherApi.APP_KEY),
            Pair("units", "metric")
        )

}