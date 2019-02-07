package com.mekamello.weatherly.domain.api

import com.google.gson.annotations.SerializedName

class DailyResponse(
    @SerializedName("city")
    val city: City,
    @SerializedName("list")
    val list: List<Item>
) {
    class City(
        @SerializedName("id")
        val id: Int,
        @SerializedName("name")
        val name: String,
        @SerializedName("coord")
        val coord: Coordinates,
        @SerializedName("country")
        val country: String
    )

    class Coordinates(
        @SerializedName("lat")
        val lat: Double,
        @SerializedName("lon")
        val lon: Double
    )

    class Item(
        @SerializedName("dt")
        val date: Long,
        @SerializedName("main")
        val temp: Temperature,
        @SerializedName("weather")
        val weather: List<WeatherResponse.Weather>
    )

    class Temperature(
        @SerializedName("temp")
        val temp: Float,
        @SerializedName("min")
        val min: Float,
        @SerializedName("max")
        val max: Float
    )
}