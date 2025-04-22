package com.alexcfa.yourweather.data

import com.alexcfa.yourweather.data.database.CurrentWeatherDao
import com.alexcfa.yourweather.data.database.HourlyForecastDao

class WeatherLocalDataSource(
    private val currentWeatherDao: CurrentWeatherDao,
    private val hourlyForecastDao: HourlyForecastDao
) {

    suspend fun saveCurrentWeather(weather: CurrentWeatherEntity) =
        currentWeatherDao.saveCurrentWeather(weather)

    suspend fun getLastCurrentWeather(): CurrentWeatherEntity? =
        currentWeatherDao.getLastCurrentWeather()

    suspend fun getCurrentWeatherByLocation(location: String): CurrentWeatherEntity? =
        currentWeatherDao.getCurrentWeatherByLocation(location)

    suspend fun deleteOldRecords(timestamp: Long) = currentWeatherDao.deleteOldRecords(timestamp)


    suspend fun saveHourlyForecasts(forecasts: List<HourlyForecastEntity>) =
        hourlyForecastDao.saveHourlyForecasts(forecasts)

    suspend fun deleteOldForecasts(location: String) = hourlyForecastDao.deleteOldForecasts(location)

    suspend fun getHourlyForecastsByLocation(location: String): List<HourlyForecastEntity> =
        hourlyForecastDao.getHourlyForecastsByLocation(location)

    suspend fun getLastUpdateTime(location: String): Long? =
        hourlyForecastDao.getLastUpdateTime(location)

    suspend fun getHourlyForecastsForDay(
        location: String,
        date: String
    ): List<HourlyForecastEntity> = hourlyForecastDao.getHourlyForecastsForDay(location, date)

    suspend fun deleteHourlyForecastsByLocation(location: String) =
        hourlyForecastDao.deleteHourlyForecastsByLocation(location)

}
