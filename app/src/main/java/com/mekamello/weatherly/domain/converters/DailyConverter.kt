package com.mekamello.weatherly.domain.converters

import com.mekamello.weatherly.domain.api.ForecastResponse
import com.mekamello.weatherly.domain.models.City
import com.mekamello.weatherly.domain.models.Forecast
import com.mekamello.weatherly.domain.models.Weather
import com.mekamello.weatherly.domain.models.Temperature

class DailyConverter(private val weatherConverter: WeatherConverter) {
    fun convert(from: ForecastResponse): Forecast =
        Forecast(
            city = City(
                id = from.city.id,
                name = from.city.name
            ),
            weathers = convertDaily(from.list)
        )

    private fun convertDaily(from: List<ForecastResponse.Item>) =
        from.map { item ->
            Weather(
                date = item.date,
                temp = convertTemperature(item.temp),
                atmosphere = item.weather.map { weatherConverter.convertWeather(it) }
            )
        }

    private fun convertTemperature(from: ForecastResponse.Temperature) =
        Temperature(
            temp = from.temp,
            min = from.min,
            max = from.max
        )

}