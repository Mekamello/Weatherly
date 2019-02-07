package com.mekamello.weatherly.domain.database.daos

import android.arch.persistence.room.*
import com.mekamello.weatherly.domain.database.entities.CityE
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface CityDao {
    @Query("SELECT * FROM cities")
    fun getAllCities():Single<List<CityE>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(city: CityE)

    @Delete
    fun delete(city: CityE)
}