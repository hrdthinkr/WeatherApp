package com.example.weatherapp.presentation.main.main_screen

import android.location.Location
import com.example.weatherapp.domain.model.ForecastType
import com.example.weatherapp.domain.model.WeatherModel
import com.example.weatherapp.R
import com.example.weatherapp.modelka.WeatherResponse

data class MainScreenState(
    var weatherResponse: WeatherResponse?=null,
    val backgroundImageId:Int=R.drawable.background,
    val hoursForecastList: List<WeatherModel>?=null,
    val error: String? = null,
    val currentLocation: Location?=null,
    val isLoading: Boolean = false,
    val selectedForecast: ForecastType = ForecastType.HOURLY
)
