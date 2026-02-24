package com.example.weatherapp.secondForecast

import com.google.gson.annotations.SerializedName

data class Day(
    @SerializedName("condition")
    val condition: Condition,
    @SerializedName("daily_chance_of_rain")
    val daily_chance_of_rain: Int,
    @SerializedName("daily_chance_of_snow")
    val daily_chance_of_snow: Int,
    @SerializedName("daily_will_it_rain")
    val daily_will_it_rain: Int,
    @SerializedName("daily_will_it_snow")
    val daily_will_it_snow: Int,
    @SerializedName("maxtemp_c")
    val maxtemp_c: Double,
    @SerializedName("maxwind_mph")
    val maxwind_mph: Double,
    @SerializedName("mintemp_c")
    val mintemp_c: Double,
    @SerializedName("totalprecip_mm")
    val totalprecip_mm: Double,
)