package com.alexcfa.yourweather.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.alexcfa.yourweather.data.CurrentWeatherEntity
import com.alexcfa.yourweather.data.HourlyForecastEntity

@Database(entities = [CurrentWeatherEntity::class, HourlyForecastEntity::class], version = 1)
abstract class WeatherDatabase : RoomDatabase() {

    abstract fun currentWeatherDao(): CurrentWeatherDao
    abstract fun hourlyForecastDao(): HourlyForecastDao

}