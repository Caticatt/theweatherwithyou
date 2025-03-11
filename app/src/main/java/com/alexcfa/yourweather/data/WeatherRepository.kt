package com.alexcfa.yourweather.data

import Current
import CurrentLocationResponse
import Hourly
import Location
import Request
import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.time.format.DateTimeFormatter


class WeatherRepository {

    companion object {
        const val UNITS = "m"
        const val INTERVAL = 1
        const val HOURLY = 1
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getActualDate(): String {
        val actualDate = LocalDate.now()
        val dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        return actualDate.format(dateFormat)
    }

    suspend fun fetchCurrentLocation(
        query: String = "Madrid, ES",
        units: String = UNITS
    ): CurrentLocationModel? =
        WeatherClient.instance.fetchCurrentLocationWeather(query, units).toDomainModel()

    //CurrentLocationResponse = WeatherClient.instance.fetchCurrentLocationWeather(query, units)


    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun fetchHourlyLocationData(
        query: String = "Madrid, ES",
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

data class HourlyModel(
    val time: String?,
    val temperature: Int?,
    val windSpeed: Int?,
    val weatherIcons: List<String>,
    val weatherDescriptions: List<String>?,
    val precip: Double?,
    val pressure: Int?
)

data class CurrentLocationModel(
    val request: Request?,
    val location: Location?,
    val current: Current?
)
