package com.example.weatherapp.domain.repository

import com.example.weatherapp.modelka.WeatherResponse

interface WeatherRepository {
    suspend fun getWeatherByCityName(city: String, apiKey: String): WeatherResponse
    suspend fun getWeatherByLocation(lat: Double, lon: Double, apiKey: String): WeatherResponse
}