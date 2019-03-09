package com.mekamello.weatherly.domain.database.daos

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.mekamello.weatherly.domain.database.entities.WeatherE

@Dao
interface WeatherDao {
    @Query(value = "DELETE FROM weathers WHERE city_id == :cityId")
    fun deleteAllWeathers(cityId: Int)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(infos: List<WeatherE>)
}