package com.mekamello.weatherly.domain.converters

import com.mekamello.weatherly.domain.database.entities.CityE
import com.mekamello.weatherly.domain.models.City

class CityConverter {
    fun convert(from: City) =
        CityE(
            uid = from.id,
            name = from.name
        )

    fun convert(from: CityE) =
            City(
                id = from.uid,
                name = from.name
            )
}