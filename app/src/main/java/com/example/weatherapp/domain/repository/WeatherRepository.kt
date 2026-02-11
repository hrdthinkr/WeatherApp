package com.example.weatherapp.domain.repository

import com.example.weatherapp.common.Resource
import com.example.weatherapp.secondForecast.secondForecast

interface WeatherRepository {
    suspend fun getWeatherByCityName(key: String, city: String): Resource<secondForecast>
    suspend fun getWeatherByLocation(key: String,lat:Double,lon:Double): Resource<secondForecast>
}