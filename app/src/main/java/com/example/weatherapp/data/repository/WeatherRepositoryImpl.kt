package com.example.weatherapp.data.repository

import com.example.weatherapp.common.Resource
import com.example.weatherapp.data.remote.WeatherApi
import com.example.weatherapp.domain.repository.WeatherRepository
import com.example.weatherapp.currentWeatherModel.WeatherResponse
import com.example.weatherapp.forecastWeatherModel.Forecast

import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val api: WeatherApi,
) : WeatherRepository {
    override suspend fun getWeatherByCityName(
        city: String,
        apiKey: String,
    ): Resource<Forecast> {
        return try {
            Resource.Success(
                data = api.getWeatherByCityName(
                    q = city,
                    key = apiKey,
                )
            )
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message ?: "An unknown error occurred.")
        }
    }

    override suspend fun getWeatherByLocation(
        lat: Double,
        lon: Double,
        apiKey: String,
    ): Resource<Forecast> {
        return try {
            Resource.Success(
                data = api.getWeatherByLocation(
                    lat = lat,
                    lon = lon,
                    key = apiKey,
                )
            )
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message ?: "An unknown error occurred.")
        }
    }

}
