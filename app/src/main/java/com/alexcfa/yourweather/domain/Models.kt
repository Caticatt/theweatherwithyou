package com.alexcfa.yourweather.domain

import com.alexcfa.yourweather.data.remote.RemoteCurrent
import com.alexcfa.yourweather.data.remote.RemoteLocation
import com.alexcfa.yourweather.data.remote.RemoteRequest

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
    val request: RemoteRequest?,
    val location: RemoteLocation?,
    val current: RemoteCurrent?
)

/*
data class CurrentWeatherModel(
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

data class HourlyForecastModel(
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
)*/
