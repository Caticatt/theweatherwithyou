package com.alexcfa.yourweather.data

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.time.format.DateTimeFormatter


class WeatherRepository (private val regionRepository: RegionRepository) {

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
        units: String = UNITS
    ): CurrentLocationModel? =
        WeatherClient.instance.fetchCurrentLocationWeather(regionRepository.findLastRegion(), units).toDomainModel()

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun fetchHourlyLocationData(
        historicalDate: String = getActualDate(),
        hourly: Int = HOURLY,
        interval: Int = INTERVAL,
        units: String = UNITS
    ): List<HourlyModel>? =
        WeatherClient.instance.fetchHistoricalWeather(
            regionRepository.findLastRegion(),
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
