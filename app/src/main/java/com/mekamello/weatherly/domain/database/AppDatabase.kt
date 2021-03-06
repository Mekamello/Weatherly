package com.mekamello.weatherly.domain.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.mekamello.weatherly.domain.database.daos.CityDao
import com.mekamello.weatherly.domain.database.daos.CityUpdateDateDao
import com.mekamello.weatherly.domain.database.daos.CityWeatherDao
import com.mekamello.weatherly.domain.database.daos.WeatherDao
import com.mekamello.weatherly.domain.database.entities.CityE
import com.mekamello.weatherly.domain.database.entities.CityUpdateDateE
import com.mekamello.weatherly.domain.database.entities.WeatherE

@Database(
    entities = [CityE::class, WeatherE::class, CityUpdateDateE::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun cityDao(): CityDao
    abstract fun cityWeatherDao(): CityWeatherDao
    abstract fun weatherInfoDao(): WeatherDao
    abstract fun cityUpdateDateDao(): CityUpdateDateDao
}