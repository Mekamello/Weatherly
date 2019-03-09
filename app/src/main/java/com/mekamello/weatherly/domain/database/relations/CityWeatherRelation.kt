package com.mekamello.weatherly.domain.database.relations

import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Relation
import com.mekamello.weatherly.domain.database.entities.CityE
import com.mekamello.weatherly.domain.database.entities.WeatherE

class CityWeatherRelation {
    @Embedded
    lateinit var city: CityE
    @Relation(parentColumn = "uid", entityColumn = "city_id")
    var weathers: List<WeatherE> = listOf()
}