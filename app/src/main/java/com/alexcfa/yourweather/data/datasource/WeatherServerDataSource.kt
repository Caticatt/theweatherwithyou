package com.alexcfa.yourweather.data.datasource

import android.os.Build
import androidx.annotation.RequiresApi
import com.alexcfa.yourweather.data.remote.CurrentLocationResponse
import com.alexcfa.yourweather.data.remote.RemoteHourly
import com.alexcfa.yourweather.data.remote.WeatherService
import com.alexcfa.yourweather.domain.CurrentLocationModel
import com.alexcfa.yourweather.domain.HourlyModel
import com.alexcfa.yourweather.ui.common.getActualDate

const val UNITS = "m"
const val INTERVAL = 1
const val HOURLY = 1

interface WeatherRemoteDataSource {
    suspend fun fetchCurrentLocation(query: String, units: String = UNITS): CurrentLocationModel

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun fetchHourlyLocationData(
        query: String,
        historicalDate: String = getActualDate(),
        hourly: Int = HOURLY,
        interval: Int = INTERVAL,
        units: String = UNITS
    ): List<HourlyModel>?
}

class WeatherServerDataSource (
    private val weatherService: WeatherService
): WeatherRemoteDataSource {

    override suspend fun fetchCurrentLocation(
        query: String,
        units: String
    ): CurrentLocationModel =
        weatherService.fetchCurrentLocationWeather(
            query,
            units
        ).toCurrentDomainModel()

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun fetchHourlyLocationData(
        query: String,
        historicalDate: String,
        hourly: Int,
        interval: Int,
        units: String
    ): List<HourlyModel>? =
        weatherService.fetchHistoricalWeather(
            query,
            historicalDate,
            hourly,
            interval,
            units
        ).historical?.values?.firstOrNull()?.hourlyList?.map {
            it.toHourlyDomainModel()
        }
}

private fun CurrentLocationResponse.toCurrentDomainModel(): CurrentLocationModel =
    CurrentLocationModel(
        request = request,
        location = location,
        current = current
    )

private fun RemoteHourly.toHourlyDomainModel(): HourlyModel = HourlyModel(
    time = time,
    temperature = temperature,
    windSpeed = windSpeed,
    weatherIcons = weatherIcons?.filterNotNull() ?: emptyList(),
    weatherDescriptions = weatherDescriptions?.filterNotNull() ?: emptyList(),
    precip = precip,
    pressure = pressure
)
