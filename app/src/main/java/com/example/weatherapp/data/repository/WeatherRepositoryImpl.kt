package com.example.weatherapp.data.repository

import com.example.weatherapp.data.remote.WeatherApi
import com.example.weatherapp.domain.repository.WeatherRepository
import com.example.weatherapp.modelka.WeatherResponse

import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val api: WeatherApi,
) : WeatherRepository {
    override suspend fun getWeatherByCityName(
        city: String,
        apiKey: String,
    ): WeatherResponse {
        return api.getWeatherByCityName(
            city = city,
            apiKey = apiKey,
        )
    }

    override suspend fun getWeatherByLocation(
        lat: Double,
        lon: Double,
        apiKey: String,
    ): WeatherResponse {
        return api.getWeatherByLocation(
            lat = lat,
            lon = lon,
            apiKey = apiKey,
        )
    }

}
