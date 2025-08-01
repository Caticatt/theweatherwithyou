package com.alexcfa.yourweather.domain

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
    val request: RequestModel?,
    val location: LocationModel?,
    val current: CurrentModel?
)

data class RequestModel(
    val type: String?,
    val query: String?,
    val language: String?,
    val unit: String?
)

data class LocationModel(
    val name: String?,
    val country: String?,
    val region: String?,
    val lat: String?,
    val lon: String?,
    val timezone_id: String?,
    val localtime: String?,
    val localtime_epoch: Int?,
    val utc_offset: String?
)

data class CurrentModel(
    val observation_time: String?,
    val temperature: Int?,
    val weather_code: Int?,
    val weather_icons: List<String>?,
    val weather_descriptions: List<String>?,
    val wind_speed: Int?,
    val wind_degree: Int?,
    val wind_dir: String?,
    val pressure: Int?,
    val precip: Double?,
    val humidity: Int?,
    val cloudcover: Int?,
    val feelslike: Int?,
    val uv_index: Int?,
    val visibility: Int?,
    val is_day: String?
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
