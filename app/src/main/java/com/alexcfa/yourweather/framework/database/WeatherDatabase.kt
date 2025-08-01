package com.alexcfa.yourweather.framework.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [DbCurrentWeather::class, DbHourlyForecast::class], version = 1, exportSchema = false)
abstract class WeatherDatabase : RoomDatabase() {

    abstract fun currentWeatherDao(): CurrentWeatherDao
    abstract fun hourlyForecastDao(): HourlyForecastDao

}