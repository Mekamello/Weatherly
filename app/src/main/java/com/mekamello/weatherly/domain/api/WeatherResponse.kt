package com.mekamello.weatherly.domain.api

import com.google.gson.annotations.SerializedName

class WeatherResponse(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("weather")
    val weather: List<Weather>,
    @SerializedName("main")
    val main: Main,
    @SerializedName("dt")
    val date: Long
) {
    class Main(
        @SerializedName("temp")
        val temp: Float,
        @SerializedName("temp_min")
        val min: Float,
        @SerializedName("temp_max")
        val max: Float
    )

    class Weather(
        @SerializedName("id")
        val id: Int,
        @SerializedName("main")
        val main: String,
        @SerializedName("description")
        val description: String
    )
}