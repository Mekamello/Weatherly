package com.mekamello.weatherly.domain.repositories

import com.mekamello.weatherly.domain.converters.CityConverter
import com.mekamello.weatherly.domain.database.daos.CityDao
import com.mekamello.weatherly.domain.database.entities.CityE
import com.mekamello.weatherly.domain.models.City
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

interface CityRepository {
    fun add(city: City): Completable
    fun addAll(cities: List<City>): Completable
    fun getAll(): Single<List<City>>
}

class CityRepositoryImpl
@Inject constructor(
    private val cityDao: CityDao,
    private val cityConverter: CityConverter
) : CityRepository {
    override fun add(city: City): Completable =
        getConvertedCityEntity(city)
            .flatMapCompletable { addCityWith(it) }

    override fun addAll(cities: List<City>): Completable =
        Completable.concat(cities.map { add(it) })

    override fun getAll(): Single<List<City>> =
        cityDao.getAllCities()
            .map { list -> list.map { cityConverter.convert(it) } }

    private fun getConvertedCityEntity(city: City) =
            Single.fromCallable { cityConverter.convert(city) }

    private fun addCityWith(city: CityE) =
            Completable.fromAction { cityDao.insert(city) }
}