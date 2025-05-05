package com.alexcfa.yourweather.data

import com.alexcfa.yourweather.data.database.CurrentWeatherDao
import com.alexcfa.yourweather.data.database.HourlyForecastDao

class WeatherLocalDataSource(
    private val currentWeatherDao: CurrentWeatherDao,
    private val hourlyForecastDao: HourlyForecastDao
) {

    suspend fun saveCurrentWeather(weather: CurrentWeatherEntity) =
        currentWeatherDao.saveCurrentWeather(weather)

    val lastCurrentWeather = currentWeatherDao.getLastCurrentWeather()

    fun getCurrentWeatherByLocation(location: String)= currentWeatherDao.getCurrentWeatherByLocation(location)

    suspend fun deleteOldRecords(timestamp: Long) = currentWeatherDao.deleteOldRecords(timestamp)


    suspend fun saveHourlyForecasts(forecasts: List<HourlyForecastEntity>) = hourlyForecastDao.saveHourlyForecasts(forecasts)

    fun getHourlyForecastsByLocation(location: String)= hourlyForecastDao.getHourlyForecastsByLocation(location)

    fun getLastUpdateTime(location: String) = hourlyForecastDao.getLastUpdateTime(location)

    fun getHourlyForecastsForDay(location: String, date: String)= hourlyForecastDao.getHourlyForecastsForDay(location, date)

    suspend fun deleteHourlyForecastsByLocation(location: String) = hourlyForecastDao.deleteHourlyForecastsByLocation(location)

}
