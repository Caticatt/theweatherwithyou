package com.alexcfa.yourweather.data

import CurrentLocationResponse
import Hourly

class WeatherRepository {

    //ACCESS_KEY = 1033329b803bec2c5d1c7cd972cc7f2d

    //https://api.weatherstack.com/current?access_key=ACCESS_KEY&query=NewYork

    //

    suspend fun fetchCurrentLocation(): CurrentLocationResponse =
        WeatherClient.instance.fetchCurrentLocationWeather("Madrid,Spain", "m")

    suspend fun fetchHourlyLocationData(): List<Hourly> =
        WeatherClient.instance.fetchHistoricalWeather(
            "Madrid,Spain",
            "2025-01-12",
            1,
            1,
            "m"
        ).historical.values.firstOrNull()?.hourly!!.map {
            it.toDomainModel()
        }
}

private fun Hourly.toDomainModel(): Hourly = Hourly(
    precip = precip,
    pressure = pressure,
    temperature = temperature,
    time = time,
    weatherDescriptions = weatherDescriptions,
    weatherIcons = weatherIcons,
    windSpeed = windSpeed
)
