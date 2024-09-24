package com.example.weatherapp.data.remote

import com.example.weatherapp.common.Constants
import com.example.weatherapp.currentWeatherModel.WeatherResponse
import com.example.weatherapp.forecastWeatherModel.Forecast
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
    @GET("forecast?")
    suspend fun getWeatherByCityName(
        @Query("q") q: String,
        @Query("appid") key: String = Constants.API_KEY,
    ): Forecast

    @GET("forecast?")
    suspend fun getWeatherByLocation(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("appid") key: String = Constants.API_KEY,
    ): Forecast
}
