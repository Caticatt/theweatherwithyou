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
                request = request?.toDomainModel(),
        location = location?.toDomainModel(),
        current = current?.toDomainModel()
    )

private fun RemoteRequest.toDomainModel(): RequestModel =
    RequestModel(
        type = type,
        query = query,
        language = language,
        unit = unit
    )

private fun RemoteLocation.toDomainModel(): LocationModel =
    LocationModel(
        name = name,
        country = country,
        region = region,
        lat = lat,
        lon = lon,
        timezone_id = timezoneId,
        localtime = localtime,
        localtime_epoch = localtimeEpoch,
        utc_offset = utcOffset
    )

private fun RemoteCurrent.toDomainModel(): CurrentModel =
    CurrentModel(
        observation_time = observationTime,
        temperature = temperature,
        weather_code = weatherCode,
        weather_icons = weatherIcons,
        weather_descriptions = weatherDescriptions,
        wind_speed = windSpeed,
        wind_degree = windDegree,
        wind_dir = windDir,
        pressure = pressure,
        precip = precip,
        humidity = humidity,
        cloudcover = cloudcover,
        feelslike = feelslike,
        uv_index = uvIndex,
        visibility = visibility,
        is_day = isDay
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