package com.mekamello.weatherly.domain.converters

import com.mekamello.weatherly.domain.models.CityDetail
import com.mekamello.weatherly.domain.models.CityMain
import com.mekamello.weatherly.utils.DateUtils
import javax.inject.Inject

class CityMainConverter
@Inject constructor(
    private val dateUtils: DateUtils
) {
    fun convert(from: CityDetail): CityMain {
        val daily = if(from.daily.size <= 1){
            from.daily.first()
        } else {
            from.daily.firstOrNull { dateUtils.isNow(it.getDateInMills()) }
        }
        val weather = daily?.weather?.firstOrNull()
        return CityMain(
            city = from.city,
            temp = daily?.temp?.temp ?: 0F,
            weatherId = weather?.id ?: -1,
            weatherMain = weather?.main ?: "",
            weatherDescription = weather?.description ?: ""
        )
    }


}