package com.alexcfa.yourweather.data.datasource

import android.os.Build
import androidx.annotation.RequiresApi
import com.alexcfa.yourweather.data.CurrentLocationModel
import com.alexcfa.yourweather.data.CurrentLocationResponse
import com.alexcfa.yourweather.data.Hourly
import com.alexcfa.yourweather.data.HourlyModel
import com.alexcfa.yourweather.data.WeatherClient
import com.alexcfa.yourweather.ui.common.getActualDate

class WeatherRemoteDataSource {

    companion object {
        const val UNITS = "m"
        const val INTERVAL = 1
        const val HOURLY = 1
    }


    suspend fun fetchCurrentLocation(
        query: String,
        units: String = UNITS
    ): CurrentLocationModel? =
        WeatherClient.instance.fetchCurrentLocationWeather(
            query,
            units
        ).toDomainModel()

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun fetchHourlyLocationData(
        query: String,
        historicalDate: String = getActualDate(),
        hourly: Int = HOURLY,
        interval: Int = INTERVAL,
        units: String = UNITS
    ): List<HourlyModel>? =
        WeatherClient.instance.fetchHistoricalWeather(
            query,
            historicalDate,
            hourly,
            interval,
            units
        ).historical?.values?.firstOrNull()?.hourlyList?.map {
            it.toDomainModel()
        }


}

private fun Hourly.toDomainModel(): HourlyModel = HourlyModel(
    time = time,
    temperature = temperature,
    windSpeed = windSpeed,
    weatherIcons = weatherIcons,
    weatherDescriptions = weatherDescriptions,
    precip = precip,
    pressure = pressure
)

private fun CurrentLocationResponse.toDomainModel(): CurrentLocationModel = CurrentLocationModel(
    request = request,
    location = location,
    current = current
)