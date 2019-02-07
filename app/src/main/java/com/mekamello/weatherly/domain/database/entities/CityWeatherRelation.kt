package com.mekamello.weatherly.domain.database.entities

import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Relation

class CityWeatherRelation {
    @Embedded
    lateinit var city: CityE
    @Relation(parentColumn = "uid", entityColumn = "city_id")
    var weathers: List<WeatherInfoE> = listOf()
}