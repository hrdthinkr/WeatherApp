package com.example.weatherapp.ExApiModells

import com.google.gson.annotations.SerializedName

data class ExModel(
    @SerializedName("current")
    val current: Current,
    @SerializedName("forecast")
    val forecast: Forecast,
    @SerializedName("location")
    val location: Location
)