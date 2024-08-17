package com.example.weatherapp.ExApiModells

data class Forecastday(
    val date: String,
    val day: Day,
    val hour: List<Hour>
)