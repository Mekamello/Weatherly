package com.mekamello.weatherly.domain.converters

import com.mekamello.weatherly.domain.api.WeatherResponse
import com.mekamello.weatherly.domain.models.*

class WeatherConverter {
    fun convert(from: WeatherResponse): CityDetail =
        CityDetail(
            city = City(
                id = from.id,
                name = from.name
            ),
            daily = listOf(convertDaily(from))

        )

    private fun convertDaily(from: WeatherResponse)=
            Daily(
                date = from.date,
                weather = from.weather.map { convertWeather(it) },
                temp = Temperature(
                    temp = from.main.temp,
                    min = from.main.min,
                    max = from.main.max
                )
            )

    fun convertWeather(from: WeatherResponse.Weather) =
        Weather(
            id = from.id,
            main = from.main,
            description = from.description
        )

}