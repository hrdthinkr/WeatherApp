package com.example.weatherapp.secondForecast

import com.google.gson.annotations.SerializedName

data class Current(
    @SerializedName("condition")
    val condition: Condition,
    @SerializedName("feelslike_c")
    val feelslike_c: Double,
    @SerializedName("humidity")
    val humidity: Int,
    @SerializedName("precip_mm")
    val precip_mm: Double,
    @SerializedName("temp_c")
    val temp_c: Double,
    @SerializedName("wind_mph")
    val wind_mph: Double,
)