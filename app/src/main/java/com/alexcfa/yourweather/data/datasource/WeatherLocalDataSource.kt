package com.alexcfa.yourweather.data.datasource

import com.alexcfa.yourweather.framework.database.DbCurrentWeather
import com.alexcfa.yourweather.framework.database.DbHourlyForecast
import kotlinx.coroutines.flow.Flow

interface WeatherLocalDataSource {
    val lastCurrentWeather: Flow<DbCurrentWeather?>
    suspend fun saveCurrentWeather(weather: DbCurrentWeather)
    fun getHourlyForecastsByLocation(location: String): Flow<List<DbHourlyForecast>>
    suspend fun saveHourlyForecasts(forecasts: List<DbHourlyForecast>)
    suspend fun deleteHourlyForecastsByLocation(location: String)
}