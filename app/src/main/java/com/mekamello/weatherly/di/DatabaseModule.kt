package com.mekamello.weatherly.di

import android.arch.persistence.room.Room
import android.content.Context
import com.mekamello.weatherly.domain.database.AppDatabase
import com.mekamello.weatherly.domain.database.daos.CityDao
import com.mekamello.weatherly.domain.database.daos.CityUpdateDateDao
import com.mekamello.weatherly.domain.database.daos.CityWeatherDao
import com.mekamello.weatherly.domain.database.daos.WeatherInfoDao
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton

@Module
class DatabaseModule {

    companion object {
        private val DATABASE_NAME = "db"
    }

    @Provides
    @Singleton
    fun provideDatabase(@Named(AppModule.APPLICATION_CONTEXT) context: Context): AppDatabase =
            Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
                    .build()

    //region Repository
    @Provides
    @Singleton
    fun provideWeatherInfoDao(database: AppDatabase): WeatherInfoDao =
            database.weatherInfoDao()

    @Provides
    @Singleton
    fun provideCityDao(database: AppDatabase): CityDao =
            database.cityDao()

    @Provides
    @Singleton
    fun provideCityUpdateDateDao(database: AppDatabase): CityUpdateDateDao =
            database.cityUpdateDateDao()

    @Provides
    @Singleton
    fun provideCityWeatherDao(database: AppDatabase): CityWeatherDao =
            database.cityWeatherDao()
    //endregion
}