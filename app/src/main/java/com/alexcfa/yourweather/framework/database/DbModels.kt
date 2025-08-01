package com.alexcfa.yourweather.framework.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "current_weather")
data class DbCurrentWeather(
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
data class DbHourlyForecast(
    @PrimaryKey(autoGenerate = false)
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
