package com.alexcfa.yourweather.domain

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

data class CurrentWeatherEntity(
    val locationName: String,
    val country: String,
    val region: String,
    val temperature: Int,
    val weatherDescription: String,
    val weatherIconUrl: String,
    val windSpeed: Int,
    val pressure: Int,
    val precip: Double,
    val observationTime: String,
    val lastUpdated: Long = System.currentTimeMillis()
)

data class HourlyForecastEntity(
    val compositeKey: String,
    val locationName: String,
    val time: String,
    val temperature: Int,
    val weatherDescription: String,
    val weatherIconUrl: String,
    val windSpeed: Int,
    val pressure: Int,
    val precip: Double,
    val lastUpdated: Long = System.currentTimeMillis()
)