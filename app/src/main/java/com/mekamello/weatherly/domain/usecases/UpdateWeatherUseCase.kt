package com.mekamello.weatherly.domain.usecases

import com.mekamello.weatherly.domain.api.WeatherApi
import com.mekamello.weatherly.domain.converters.DailyConverter
import com.mekamello.weatherly.domain.models.Forecast
import com.mekamello.weatherly.domain.models.CityUpdateDate
import com.mekamello.weatherly.domain.repositories.ForecastRepository
import com.mekamello.weatherly.domain.repositories.CityUpdateDateRepository
import com.mekamello.weatherly.domain.repositories.WeatherRepository
import com.mekamello.weatherly.utils.DateUtils
import io.reactivex.Completable
import javax.inject.Inject

interface UpdateWeatherUseCase {
    fun execute(cityId: Int): Completable
}

class UpdateWeatherUseCaseImpl
@Inject constructor(
    private val weatherApi: WeatherApi,
    private val weatherRepository: WeatherRepository,
    private val forecastRepository: ForecastRepository,
    private val cityUpdateDateRepository: CityUpdateDateRepository,
    private val dailyConverter: DailyConverter,
    private val dateUtils: DateUtils
) : UpdateWeatherUseCase {
    override fun execute(cityId: Int): Completable =
        forecastRepository.get(cityId)
            .flatMapCompletable { updateIfNeed(it) }

    private fun updateIfNeed(forecast: Forecast) =
        if (forecast.weathers.size <= 1) {
            update(forecast.city.id)
        } else {
            cityUpdateDateRepository.get(forecast.city.id)
                .flatMapCompletable { updateIfNeed(it) }
        }

    private fun updateIfNeed(cityUpdateDate: CityUpdateDate) =
        if (dateUtils.isPastTenMinutes(cityUpdateDate.date)) {
            weatherRepository.deleteBy(cityUpdateDate.cityId)
                .andThen(update(cityUpdateDate.cityId))
        } else {
            Completable.complete()
        }

    private fun update(cityId: Int) =
        weatherApi.getForecast(request(cityId))
            .map { dailyConverter.convert(it) }
            .flatMapCompletable { addUpdatedWeather(it) }

    private fun addUpdatedWeather(forecast: Forecast) =
        forecastRepository.add(forecast)
            .andThen(cityUpdateDateRepository.add(forecast.city.id, dateUtils.getCurrentTimeInMills()))

    private fun request(cityId: Int): Map<String, String> =
        mapOf(
            Pair("id", cityId.toString()),
            Pair("APPID", WeatherApi.APP_KEY),
            Pair("units", "metric")
        )

}