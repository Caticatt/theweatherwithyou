package com.alexcfa.yourweather.framework

import com.alexcfa.yourweather.data.datasource.WeatherLocalDataSource
import com.alexcfa.yourweather.framework.database.CurrentWeatherDao
import com.alexcfa.yourweather.framework.database.DbCurrentWeather
import com.alexcfa.yourweather.framework.database.DbHourlyForecast
import com.alexcfa.yourweather.framework.database.HourlyForecastDao

class WeatherRoomDataSource(
    private val currentWeatherDao: CurrentWeatherDao,
    private val hourlyForecastDao: HourlyForecastDao
) : WeatherLocalDataSource {

    override val lastCurrentWeather = currentWeatherDao.getLastCurrentWeather()

    override suspend fun saveCurrentWeather(weather: DbCurrentWeather) =
        currentWeatherDao.saveCurrentWeather(weather)


    fun getCurrentWeatherByLocation(location: String) =
        currentWeatherDao.getCurrentWeatherByLocation(location)

    suspend fun deleteOldRecords(timestamp: Long) = currentWeatherDao.deleteOldRecords(timestamp)


    override fun getHourlyForecastsByLocation(location: String) =
        hourlyForecastDao.getHourlyForecastsByLocation(location)

    override suspend fun saveHourlyForecasts(forecasts: List<DbHourlyForecast>) =
        hourlyForecastDao.saveHourlyForecasts(forecasts)

    override suspend fun deleteHourlyForecastsByLocation(location: String) =
        hourlyForecastDao.deleteHourlyForecastsByLocation(location)


    fun getLastUpdateTime(location: String) = hourlyForecastDao.getLastUpdateTime(location)
    fun getHourlyForecastsForDay(location: String, date: String) =
        hourlyForecastDao.getHourlyForecastsForDay(location, date)

}