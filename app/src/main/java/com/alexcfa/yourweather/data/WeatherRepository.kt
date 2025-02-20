package com.alexcfa.yourweather.data

import CurrentLocationResponse
import Hourly
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
    ): CurrentLocationResponse =
        WeatherClient.instance.fetchCurrentLocationWeather(query, units)

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun fetchHourlyLocationData(
        query: String = "Madrid, ES",
        historicalDate: String = getActualDate(),
        hourly: Int = HOURLY,
        interval: Int = INTERVAL,
        units: String = UNITS
    ): List<Hourly> =
        WeatherClient.instance.fetchHistoricalWeather(
            query,
            historicalDate,
            hourly,
            interval,
            units
        ).historical.values.firstOrNull()?.hourly!!.map {
            it.toDomainModel()
        }
}

private fun Hourly.toDomainModel(): Hourly = Hourly(
    time = time,
    temperature = temperature,
    windSpeed = windSpeed,
    weatherIcons = weatherIcons,
    weatherDescriptions = weatherDescriptions,
    precip = precip,
    pressure = pressure
)
