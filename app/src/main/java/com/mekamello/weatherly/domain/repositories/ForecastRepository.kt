package com.mekamello.weatherly.domain.repositories

import com.mekamello.weatherly.domain.converters.ForecastConverter
import com.mekamello.weatherly.domain.database.daos.CityWeatherDao
import com.mekamello.weatherly.domain.database.daos.WeatherDao
import com.mekamello.weatherly.domain.models.Forecast
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

interface ForecastRepository {
    fun add(forecast: Forecast): Completable
    fun get(cityId: Int): Single<Forecast>
    fun addAll(list: List<Forecast>): Completable
    fun getAll(): Single<List<Forecast>>
}

class ForecastRepositoryImpl
@Inject constructor(
    private val weatherDao: WeatherDao,
    private val cityWeatherDao: CityWeatherDao,
    private val forecastConverter: ForecastConverter
): ForecastRepository{
    override fun add(forecast: Forecast): Completable =
        extractWeatherInfoAndSave(forecast)

    override fun get(cityId: Int): Single<Forecast> =
        cityWeatherDao.getCityWeatherRelation(cityId)
            .map { forecastConverter.convert(it) }

    override fun addAll(list: List<Forecast>): Completable =
        Completable.concat(list.map { add(it) })

    override fun getAll(): Single<List<Forecast>> =
            cityWeatherDao.getCityWeatherRelations()
                .map { list -> list.map { forecastConverter.convert(it) } }

    private fun extractWeatherInfoAndSave(forecast: Forecast) =
            Completable.fromAction { weatherDao.insert(forecastConverter.convert(forecast)) }
}