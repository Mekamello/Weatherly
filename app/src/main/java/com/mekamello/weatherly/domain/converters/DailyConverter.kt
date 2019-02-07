package com.mekamello.weatherly.domain.converters

import com.mekamello.weatherly.domain.api.DailyResponse
import com.mekamello.weatherly.domain.models.City
import com.mekamello.weatherly.domain.models.CityDetail
import com.mekamello.weatherly.domain.models.Daily
import com.mekamello.weatherly.domain.models.Temperature

class DailyConverter(private val weatherConverter: WeatherConverter) {
    fun convert(from: DailyResponse): CityDetail =
        CityDetail(
            city = City(
                id = from.city.id,
                name = from.city.name
            ),
            daily = convertDaily(from.list)
        )

    private fun convertDaily(from: List<DailyResponse.Item>) =
        from.map { item ->
            Daily(
                date = item.date,
                temp = convertTemperature(item.temp),
                weather = item.weather.map { weatherConverter.convertWeather(it) }
            )
        }

    private fun convertTemperature(from: DailyResponse.Temperature) =
        Temperature(
            temp = from.temp,
            min = from.min,
            max = from.max
        )

}