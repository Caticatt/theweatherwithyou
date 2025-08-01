package com.alexcfa.yourweather.framework.remote

import android.os.Build
import androidx.annotation.RequiresApi
import com.alexcfa.yourweather.data.datasource.WeatherRemoteDataSource
import com.alexcfa.yourweather.domain.CurrentLocationModel
import com.alexcfa.yourweather.domain.CurrentModel
import com.alexcfa.yourweather.domain.HourlyModel
import com.alexcfa.yourweather.domain.LocationModel
import com.alexcfa.yourweather.domain.RequestModel

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
        request = request as RequestModel?,
        location = location as LocationModel?,
        current = current as CurrentModel?
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