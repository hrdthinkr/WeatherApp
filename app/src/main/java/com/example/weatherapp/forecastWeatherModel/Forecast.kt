package com.example.weatherapp.forecastWeatherModel

import com.google.gson.annotations.SerializedName

data class Forecast(
    @SerializedName("city")
    val city: City,
    @SerializedName("list")
    val list: List<ForecastData>,
)
data class ForecastData(
    @SerializedName("main")
    val main: Main,
    @SerializedName("weather")
    val weather: List<Weather>,
    @SerializedName("dt_txt")
    val date:String,
    @SerializedName("wind")
    val wind: Wind
)
data class Main(
    @SerializedName("feels_like")
    val feels_like: Double,
    @SerializedName("humidity")
    val humidity: Int,
    @SerializedName("temp")
    val temp: Double,
)
data class City(
    @SerializedName("country")
    val country: String,
    @SerializedName("name")
    val name: String,
)
data class Wind(
    @SerializedName("speed")
    val speed: Double
)
data class Weather(
    @SerializedName("description")
    val description: String,
    @SerializedName("icon")
    val icon: String,
)
