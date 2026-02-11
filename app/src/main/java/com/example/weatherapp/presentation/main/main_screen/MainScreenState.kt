package com.example.weatherapp.presentation.main.main_screen

import android.location.Location
import com.example.weatherapp.domain.model.ForecastType
import com.example.weatherapp.R
import com.example.weatherapp.forecastWeatherModel.Forecast
import com.example.weatherapp.secondForecast.secondForecast

data class MainScreenState(
    val forecast: secondForecast?=null,
    val backgroundImageId:Int=R.drawable.background,
    val error: String? = null,
    val currentLocation: Location?=null,
    val isLoading: Boolean = false,
    val selectedForecast: ForecastType = ForecastType.HOURLY
)
