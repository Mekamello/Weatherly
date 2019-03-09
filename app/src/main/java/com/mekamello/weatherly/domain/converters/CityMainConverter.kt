package com.mekamello.weatherly.domain.converters

import com.mekamello.weatherly.domain.models.Forecast
import com.mekamello.weatherly.domain.models.CityShortWeather
import com.mekamello.weatherly.utils.DateUtils
import javax.inject.Inject

class CityMainConverter
@Inject constructor(
    private val dateUtils: DateUtils
) {
    fun convert(from: Forecast): CityShortWeather {
        val daily = if(from.weathers.size <= 1){
            from.weathers.firstOrNull()
        } else {
            from.weathers.firstOrNull { dateUtils.isNow(it.getDateInMills()) }
        }
        val weather = daily?.atmosphere?.firstOrNull()
        return CityShortWeather(
            city = from.city,
            temp = daily?.temp?.temp ?: 0F,
            weatherId = weather?.id ?: -1,
            weatherMain = weather?.main ?: "",
            weatherDescription = weather?.description ?: ""
        )
    }


}