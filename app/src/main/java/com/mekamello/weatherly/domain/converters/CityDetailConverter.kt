package com.mekamello.weatherly.domain.converters

import com.mekamello.weatherly.domain.database.entities.CityWeatherRelation
import com.mekamello.weatherly.domain.database.entities.TemperatureE
import com.mekamello.weatherly.domain.database.entities.WeatherE
import com.mekamello.weatherly.domain.database.entities.WeatherInfoE
import com.mekamello.weatherly.domain.models.*
import javax.inject.Inject

class CityDetailConverter
@Inject constructor(
    private val cityConverter: CityConverter
) {
    fun convert(from: CityDetail) =
           from.daily.map { convertDaily(it, from.city) }

    fun convert(from: CityWeatherRelation) =
        CityDetail(
            city = cityConverter.convert(from.city),
            daily = from.weathers.map { convertWeatherInfoEntity(it) }
        )

    private fun convertWeatherInfoEntity(from: WeatherInfoE) =
        Daily(
            date = from.date,
            weather = listOf(convertWeatherEntity(from.weather)),
            temp = convertTemperatureEntity(from.temperature)
        )

    private fun convertWeatherEntity(from: WeatherE) =
        Weather(
            id = from.id,
            main = from.main,
            description = from.description
        )

    private fun convertTemperatureEntity(from: TemperatureE) =
        Temperature(
            temp = from.temp,
            min = from.min,
            max = from.max
        )

    private fun convertDaily(from: Daily, relation: City) =
        WeatherInfoE(
            id = 0,
            cid = relation.id,
            date = from.date,
            weather = convertWeather(from.weather.first()),
            temperature = convertTemperature(from.temp)
        )

    private fun convertWeather(from: Weather) =
        WeatherE(
            id = from.id,
            main = from.main,
            description = from.description
        )

    private fun convertTemperature(from: Temperature) =
        TemperatureE(
            temp = from.temp,
            min = from.min,
            max = from.max
        )
}