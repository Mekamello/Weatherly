package com.mekamello.weatherly.domain.repositories

import com.mekamello.weatherly.domain.database.daos.WeatherDao
import io.reactivex.Completable
import javax.inject.Inject

interface WeatherRepository {
    fun deleteBy(cityId: Int): Completable
}

class WeatherRepositoryImpl
@Inject constructor(
    private val weatherDao: WeatherDao
): WeatherRepository{
    override fun deleteBy(cityId: Int): Completable =
            Completable.fromAction { weatherDao.deleteAllWeathers(cityId) }
}