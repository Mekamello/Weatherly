package com.mekamello.weatherly.domain.repositories

import com.mekamello.weatherly.domain.database.daos.WeatherInfoDao
import io.reactivex.Completable
import javax.inject.Inject

interface WeatherInfoRepository {
    fun deleteBy(cityId: Int): Completable
}

class WeatherInfoRepositoryImpl
@Inject constructor(
    private val weatherInfoDao: WeatherInfoDao
): WeatherInfoRepository{
    override fun deleteBy(cityId: Int): Completable =
            Completable.fromAction { weatherInfoDao.deleteAllWeathers(cityId) }
}