package com.mekamello.weatherly.domain.api

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface WeatherApi {
    companion object {
        const val APP_KEY = "1904a0ea19bfe872d99767d7b13abc95"
        const val COUNT_DAYS = 7
    }

    @GET("forecast")
    fun getDaily(@QueryMap query: Map<String, String>): Observable<DailyResponse>

    @GET("weather")
    fun getWeather(@QueryMap query: Map<String, String>): Observable<WeatherResponse>
}