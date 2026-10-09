package com.example.weatherapp.data.remote

import com.example.weatherapp.common.Constants
import com.example.weatherapp.secondForecast.secondForecast
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
    @GET("forecast.json?")
    suspend fun getWeatherByCityName(
        @Query("key") key: String = Constants.API_KEY,
        @Query("q") q: String,
        @Query("days") days:Int=10,
    ): secondForecast

    @GET("forecast.json")
    suspend fun getWeatherByLocation(
        @Query("key") key: String = Constants.API_KEY,
        @Query("q") location: String,
        @Query("days") days: Int = 7,
    ): secondForecast
}
