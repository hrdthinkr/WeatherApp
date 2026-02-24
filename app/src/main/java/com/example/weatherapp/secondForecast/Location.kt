package com.example.weatherapp.secondForecast

import com.google.gson.annotations.SerializedName

data class Location(
    @SerializedName("city")
    val country: String,
    @SerializedName("localtime")
    val localtime: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("region")
    val region: String,
)