package com.alexcfa.yourweather.data

import com.alexcfa.yourweather.data.remote.Current
import com.alexcfa.yourweather.data.remote.Location
import com.alexcfa.yourweather.data.remote.Request
import androidx.room.Entity
import androidx.room.PrimaryKey

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

@Entity(tableName = "current_weather")
data class CurrentWeatherEntity(
    @PrimaryKey
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

@Entity(tableName = "hourly_forecast")
data class HourlyForecastEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
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
