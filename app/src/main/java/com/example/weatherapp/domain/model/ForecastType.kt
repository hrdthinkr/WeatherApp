package com.example.weatherapp.domain.model

import androidx.annotation.StringRes
import com.example.weatherapp.R

enum class ForecastType(
    @StringRes val textId: Int,
) {
    HOURLY(R.string.hourly),
    TOMORROW(R.string.tomorrow),
    WEEK(R.string.week)
}