package com.alexcfa.yourweather.data

import com.alexcfa.yourweather.data.database.CurrentWeatherDao
import com.alexcfa.yourweather.data.database.DbCurrentWeather
import com.alexcfa.yourweather.data.database.DbHourlyForecast
import com.alexcfa.yourweather.data.database.HourlyForecastDao
import kotlinx.coroutines.flow.Flow


interface WeatherLocalDataSource {
    val lastCurrentWeather: Flow<DbCurrentWeather?>
    suspend fun saveCurrentWeather(weather: DbCurrentWeather)
    fun getHourlyForecastsByLocation(location: String): Flow<List<DbHourlyForecast>>
    suspend fun saveHourlyForecasts(forecasts: List<DbHourlyForecast>)
    suspend fun deleteHourlyForecastsByLocation(location: String)
}

class WeatherRoomDataSource(
    private val currentWeatherDao: CurrentWeatherDao,
    private val hourlyForecastDao: HourlyForecastDao
) : WeatherLocalDataSource {

    override val lastCurrentWeather = currentWeatherDao.getLastCurrentWeather()

    override suspend fun saveCurrentWeather(weather: DbCurrentWeather) =
        currentWeatherDao.saveCurrentWeather(weather)


    fun getCurrentWeatherByLocation(location: String)= currentWeatherDao.getCurrentWeatherByLocation(location)
    suspend fun deleteOldRecords(timestamp: Long) = currentWeatherDao.deleteOldRecords(timestamp)



    override fun getHourlyForecastsByLocation(location: String)= hourlyForecastDao.getHourlyForecastsByLocation(location)

    override suspend fun saveHourlyForecasts(forecasts: List<DbHourlyForecast>) = hourlyForecastDao.saveHourlyForecasts(forecasts)

    override suspend fun deleteHourlyForecastsByLocation(location: String) = hourlyForecastDao.deleteHourlyForecastsByLocation(location)


    fun getLastUpdateTime(location: String) = hourlyForecastDao.getLastUpdateTime(location)
    fun getHourlyForecastsForDay(location: String, date: String)= hourlyForecastDao.getHourlyForecastsForDay(location, date)

}

