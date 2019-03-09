package com.mekamello.weatherly.domain.converters

import com.mekamello.weatherly.domain.database.relations.CityWeatherRelation
import com.mekamello.weatherly.domain.database.entities.TemperatureE
import com.mekamello.weatherly.domain.database.entities.WeatherE
import com.mekamello.weatherly.domain.models.*
import javax.inject.Inject

class ForecastConverter
@Inject constructor(
    private val cityConverter: CityConverter
) {
    fun convert(from: Forecast) =
           from.weathers.map { convertDaily(it, from.city) }

    fun convert(from: CityWeatherRelation) =
        Forecast(
            city = cityConverter.convert(from.city),
            weathers = from.weathers.map { convertWeatherInfoEntity(it) }
        )

    private fun convertWeatherInfoEntity(from: WeatherE) =
        Weather(
            date = from.date,
            atmosphere = listOf(convertWeatherEntity(from.atmosphere)),
            temp = convertTemperatureEntity(from.temperature)
        )

    private fun convertWeatherEntity(from: WeatherE) =
        Atmosphere(
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

    private fun convertDaily(from: Weather, relation: City) =
        WeatherE(
            id = 0,
            cid = relation.id,
            date = from.date,
            atmosphere = convertWeather(from.atmosphere.first()),
            temperature = convertTemperature(from.temp)
        )

    private fun convertWeather(from: Atmosphere) =
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