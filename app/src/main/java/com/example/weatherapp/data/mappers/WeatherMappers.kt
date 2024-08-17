package com.example.weatherapp.data.mappers

import com.example.weatherapp.domain.model.DayInfo

private data class IndexedWeatherData(
    val index: Int,
    val data: DayInfo
)