package com.example.weatherapp.domain.usecase

import com.example.weatherapp.domain.repository.LocationRepository
import com.example.weatherapp.domain.repository.WeatherRepository
import com.example.weatherapp.currentWeatherModel.WeatherResponse
import javax.inject.Inject

class GetForecastByLocationUseCase @Inject constructor(
    private val locationRepository: LocationRepository,
    private val weatherRepository: WeatherRepository,
) :UseCase<WeatherResponse>(){

}