package com.mekamello.weatherly.domain.database.daos

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query
import com.mekamello.weatherly.domain.database.entities.CityWeatherRelation
import io.reactivex.Single

@Dao
interface CityWeatherDao {
    @Query(value = "SELECT * FROM cities")
    fun getCityWeatherRelations(): Single<List<CityWeatherRelation>>

    @Query(value = "SELECT * FROM cities WHERE uid == :cityId LIMIT 1")
    fun getCityWeatherRelation(cityId: Int): Single<CityWeatherRelation>
}