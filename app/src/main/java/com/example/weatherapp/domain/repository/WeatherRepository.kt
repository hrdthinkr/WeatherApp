package com.example.weatherapp.domain.repository

import com.example.weatherapp.common.Resource
import com.example.weatherapp.currentWeatherModel.WeatherResponse
import com.example.weatherapp.forecastWeatherModel.Forecast

interface WeatherRepository {
    suspend fun getWeatherByCityName(city: String, apiKey: String): Resource<Forecast>
    suspend fun getWeatherByLocation(lat: Double, lon: Double, apiKey: String): Resource<Forecast>
}