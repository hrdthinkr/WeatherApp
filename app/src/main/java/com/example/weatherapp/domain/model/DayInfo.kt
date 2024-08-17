package com.example.weatherapp.domain.model

data class DayInfo(
    val city:String,
    val country:String,
    val imageUrl: String,
    val condition:String,
    val windSpeed:String,
    val realFeel: String,
    val humidity: String,
    val temp: String,
)