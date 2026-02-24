package com.example.weatherapp.secondForecast

import com.google.gson.annotations.SerializedName

data class secondForecast(
    @SerializedName("current")
    val current: Current,
    @SerializedName("forecast")
    val forecast: Forecast,
    @SerializedName("location")
    val location: Location
)