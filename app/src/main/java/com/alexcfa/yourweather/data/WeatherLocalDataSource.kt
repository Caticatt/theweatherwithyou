package com.alexcfa.yourweather.data

import com.alexcfa.yourweather.data.database.CurrentWeatherDao
import com.alexcfa.yourweather.data.database.HourlyForecastDao

class WeatherLocalDataSource(
    private val currentWeatherDao: CurrentWeatherDao,
    private val hourlyForecastDao: HourlyForecastDao
) {

    suspend fun insertCurrentWeather(weather: CurrentWeatherEntity) {
        currentWeatherDao.insertCurrentWeather(weather)
    }

    suspend fun getLastCurrentWeather(): CurrentWeatherEntity? {
        return currentWeatherDao.getLastCurrentWeather()
    }

    suspend fun getCurrentWeatherByLocation(location: String): CurrentWeatherEntity? {
        return currentWeatherDao.getCurrentWeatherByLocation(location)
    }

    suspend fun deleteOldRecords(timestamp: Long) {
        currentWeatherDao.deleteOldRecords(timestamp)
    }

    suspend fun insertHourlyForecasts(forecasts: List<HourlyForecastEntity>) {
        hourlyForecastDao.insertHourlyForecasts(forecasts)
    }

    suspend fun getHourlyForecastsByLocation(location: String): List<HourlyForecastEntity> {
        return hourlyForecastDao.getHourlyForecastsByLocation(location)
    }

    suspend fun deleteHourlyForecastsByLocation(location: String) {
        hourlyForecastDao.deleteHourlyForecastsByLocation(location)
    }

    suspend fun getHourlyForecastsForDay(
        location: String,
        date: String
    ): List<HourlyForecastEntity> {
        return hourlyForecastDao.getHourlyForecastsForDay(location, date)
    }

}
