package com.example.weatherapp.data.repository

import com.example.weatherapp.common.Resource
import com.example.weatherapp.data.remote.WeatherApi
import com.example.weatherapp.domain.repository.WeatherRepository
import com.example.weatherapp.secondForecast.secondForecast

import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val api: WeatherApi,
) : WeatherRepository {
    override suspend fun getWeatherByCityName(
        key: String,
        city: String,
    ): Resource<secondForecast> {
        return try {
            Resource.Success(
                data = api.getWeatherByCityName(
                    q = city,
                    key = key,
                )
            )
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message ?: "An unknown error occurred.")
        }
    }

    override suspend fun getWeatherByLocation(
        key: String,
        lat: Double,
        lon: Double,
    ): Resource<secondForecast> {
        return try {
            Resource.Success(
                data = api.getWeatherByLocation(
                    key = key,
                    location = "$lat,$lon",
                )
            )
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message ?: "An unknown error occurred.")
        }
    }

}
