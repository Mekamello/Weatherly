package com.mekamello.weatherly.domain.converters

import com.mekamello.weatherly.domain.api.WeatherResponse
import com.mekamello.weatherly.domain.models.*

class WeatherConverter {
    fun convert(from: WeatherResponse): Forecast =
        Forecast(
            city = City(
                id = from.id,
                name = from.name
            ),
            weathers = listOf(convertDaily(from))

        )

    private fun convertDaily(from: WeatherResponse)=
            Weather(
                date = from.date,
                atmosphere = from.weather.map { convertWeather(it) },
                temp = Temperature(
                    temp = from.main.temp,
                    min = from.main.min,
                    max = from.main.max
                )
            )

    fun convertWeather(from: WeatherResponse.Weather) =
        Atmosphere(
            id = from.id,
            main = from.main,
            description = from.description
        )

}