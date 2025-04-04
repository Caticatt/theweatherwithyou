package com.alexcfa.yourweather.data

import com.alexcfa.yourweather.data.remote.Current
import com.alexcfa.yourweather.data.remote.Location
import com.alexcfa.yourweather.data.remote.Request

data class HourlyModel(
    val time: String?,
    val temperature: Int?,
    val windSpeed: Int?,
    val weatherIcons: List<String>,
    val weatherDescriptions: List<String>?,
    val precip: Double?,
    val pressure: Int?
)

data class CurrentLocationModel(
    val request: Request?,
    val location: Location?,
    val current: Current?
)
