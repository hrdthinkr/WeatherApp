package com.example.weatherapp.data.remote

import com.example.weatherapp.common.Constants
import com.example.weatherapp.domain.model.DayInfo
import com.example.weatherapp.modelka.WeatherResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
    @GET("weather?")
    suspend fun getWeatherByCityName(
        @Query("q") city:String,
        @Query("appid") apiKey:String=Constants.API_KEY,
    ): WeatherResponse

    @GET("weather?")
    suspend fun getWeatherByLocation(
        @Query("lat") lat:Double,
        @Query("lon") lon:Double,
        @Query("appid") apiKey:String=Constants.API_KEY,
    ): WeatherResponse

}
