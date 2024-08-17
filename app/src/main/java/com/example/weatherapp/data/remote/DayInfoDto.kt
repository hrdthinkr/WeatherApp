package com.example.weatherapp.data.remote

import com.example.weatherapp.domain.model.DayInfo
import com.squareup.moshi.Json

data class DayInfoDto(
    @field:Json(name = "name")
    val city: String,
    @field:Json(name = "country")
    val country: String,
    @field:Json(name = "icon")
    val imageUrl: String,
    @field:Json(name = "text")
    val condition: String,
    @field:Json(name = "wind_mph")
    val windSpeed: String,
    @field:Json(name = "temperature_2m")
    val realFeel: String,
    @field:Json(name = "humidity")
    val humidity: String,
    @field:Json(name = "temperature")
    val temp_c: String,
)

fun DayInfoDto.toDayInfo(): DayInfo {
    return DayInfo(
        imageUrl = imageUrl,
        city = city,
        country = country,
        condition = condition,
        windSpeed = windSpeed,
        realFeel = realFeel,
        humidity = humidity,
        temp = temp_c,
    )
}