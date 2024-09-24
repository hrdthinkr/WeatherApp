package com.example.weatherapp.currentWeatherModel

import com.google.gson.annotations.SerializedName

data class WeatherResponse(
    @SerializedName("main")
    val main: Main,
    @SerializedName("name")
    val name: String="",
    @SerializedName("weather")
    val weather: List<Weather>,
    @SerializedName("wind")
    val wind: Wind,
    @SerializedName("sys")
    val sys:Sys,
)

data class Main(
    @SerializedName("humidity")
    val humidity: Int,
    @SerializedName("temp")
    val temp: Double,
    @SerializedName("feels_like")
    val feels_like: Double,
)
data class Sys(
    @SerializedName("country")
    val country:String,
)

data class Wind(
    @SerializedName("speed")
    val speed: Double,
)

data class Weather(
    @SerializedName("description")
    val description: String,
    @SerializedName("icon")
    val icon: String,
)
