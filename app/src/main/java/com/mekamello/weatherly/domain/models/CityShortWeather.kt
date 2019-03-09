package com.mekamello.weatherly.domain.models

data class CityShortWeather(
    val city: City,
    val temp: Float,
    val weatherId: Int,
    val weatherMain: String,
    val weatherDescription: String
){
    fun getTempString(): String {
        val tempValue = temp.toInt()
        return if(tempValue > 0) {
            "+$tempValue"
        } else {
            tempValue.toString()
        }
    }
}