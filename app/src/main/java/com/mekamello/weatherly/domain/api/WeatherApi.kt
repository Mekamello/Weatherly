package com.mekamello.weatherly.domain.api

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
    companion object {
        const val APP_KEY = "1904a0ea19bfe872d99767d7b13abc95"
        const val COUNT_DAYS = 7
    }

    @GET("forecast")
    fun getForecast(
        @Query("APPID") appId: String = APP_KEY,
        @Query("units") units: String = "metric",
        @Query("id") cityId: Int
    ): Observable<ForecastResponse>

    @GET("weather")
    fun getWeather(
        @Query("APPID") appId: String = APP_KEY,
        @Query("units") units: String = "metric",
        @Query("id") cityId: Int
    ): Observable<WeatherResponse>

    @GET("weather")
    fun getWeather(
        @Query("APPID") appId: String = APP_KEY,
        @Query("units") units: String = "metric",
        @Query("q") cityName: String
    ): Observable<WeatherResponse>
}