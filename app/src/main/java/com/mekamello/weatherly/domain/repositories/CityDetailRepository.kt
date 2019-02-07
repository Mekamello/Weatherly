package com.mekamello.weatherly.domain.repositories

import com.mekamello.weatherly.domain.converters.CityDetailConverter
import com.mekamello.weatherly.domain.database.daos.CityWeatherDao
import com.mekamello.weatherly.domain.database.daos.WeatherInfoDao
import com.mekamello.weatherly.domain.models.CityDetail
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

interface CityDetailRepository {
    fun add(cityDetail: CityDetail): Completable
    fun get(cityId: Int): Single<CityDetail>
    fun addAll(list: List<CityDetail>): Completable
    fun getAll(): Single<List<CityDetail>>
}

class CityDetailRepositoryImpl
@Inject constructor(
    private val weatherInfoDao: WeatherInfoDao,
    private val cityWeatherDao: CityWeatherDao,
    private val cityDetailConverter: CityDetailConverter
): CityDetailRepository{
    override fun add(cityDetail: CityDetail): Completable =
        extractWeatherInfoAndSave(cityDetail)

    override fun get(cityId: Int): Single<CityDetail> =
        cityWeatherDao.getCityWeatherRelation(cityId)
            .map { cityDetailConverter.convert(it) }

    override fun addAll(list: List<CityDetail>): Completable =
        Completable.concat(list.map { add(it) })

    override fun getAll(): Single<List<CityDetail>> =
            cityWeatherDao.getCityWeatherRelations()
                .map { list -> list.map { cityDetailConverter.convert(it) } }

    private fun extractWeatherInfoAndSave(cityDetail: CityDetail) =
            Completable.fromAction { weatherInfoDao.insert(cityDetailConverter.convert(cityDetail)) }
}