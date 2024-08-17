package com.example.weatherapp.domain.model

data class WeatherModel(
    val city:String,
    val time:String,
    val humidity:String,
    val iconUrl:String,
    val temperature:String,
    val hours:String,
)