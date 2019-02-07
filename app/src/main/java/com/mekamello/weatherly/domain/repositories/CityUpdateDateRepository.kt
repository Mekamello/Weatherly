package com.mekamello.weatherly.domain.repositories

import com.mekamello.weatherly.domain.database.daos.CityUpdateDateDao
import com.mekamello.weatherly.domain.database.entities.CityUpdateDateE
import com.mekamello.weatherly.domain.models.CityUpdateDate
import io.reactivex.Completable
import io.reactivex.Maybe
import javax.inject.Inject

interface CityUpdateDateRepository {
    fun add(cityId: Int, date: Long): Completable
    fun get(cityId: Int): Maybe<CityUpdateDate>
}

class CityUpdateDateRepositoryImpl
@Inject constructor(
    private val cityUpdateDateDao: CityUpdateDateDao
) : CityUpdateDateRepository {
    override fun add(cityId: Int, date: Long): Completable =
        Completable.fromAction { cityUpdateDateDao.insert(CityUpdateDateE(cityId, date)) }

    override fun get(cityId: Int): Maybe<CityUpdateDate> =
        cityUpdateDateDao.getUpdateDate(cityId)
            .map { CityUpdateDate(it.cid, it.date) }
}