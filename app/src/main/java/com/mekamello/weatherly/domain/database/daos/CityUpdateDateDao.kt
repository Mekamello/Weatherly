package com.mekamello.weatherly.domain.database.daos

import android.arch.persistence.room.*
import com.mekamello.weatherly.domain.database.entities.CityUpdateDateE
import io.reactivex.Maybe

@Dao
interface CityUpdateDateDao {
    @Query(value = "SELECT * FROM updates WHERE cid == :cityId LIMIT 1")
    fun getUpdateDate(cityId: Int): Maybe<CityUpdateDateE>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(updateDateE: CityUpdateDateE)

    @Delete
    fun delete(updateDateE: CityUpdateDateE)
}