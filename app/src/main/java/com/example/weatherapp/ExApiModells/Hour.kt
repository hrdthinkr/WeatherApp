package com.example.weatherapp.ExApiModells

data class Hour(
    val cloud: Int,
    val condition: Condition,
    val feelslike_c: Double,
    val humidity: Int,
    val precip_mm: Double,
    val temp_c: Double,
    val time: String,
    val wind_mph: Double,
)
