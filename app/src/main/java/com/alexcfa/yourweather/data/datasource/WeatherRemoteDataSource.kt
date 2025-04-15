package com.alexcfa.yourweather.data.datasource

import android.os.Build
import androidx.annotation.RequiresApi
import com.alexcfa.yourweather.data.CurrentLocationModel
import com.alexcfa.yourweather.data.CurrentWeatherEntity
import com.alexcfa.yourweather.data.HourlyForecastEntity
import com.alexcfa.yourweather.data.remote.CurrentLocationResponse
import com.alexcfa.yourweather.data.remote.Hourly
import com.alexcfa.yourweather.data.HourlyModel
import com.alexcfa.yourweather.data.remote.Current
import com.alexcfa.yourweather.data.remote.Location
import com.alexcfa.yourweather.data.remote.WeatherClient
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
    ): CurrentLocationModel =
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
    weatherIcons = weatherIcons?.filterNotNull() ?: emptyList(),
    weatherDescriptions = weatherDescriptions?.filterNotNull() ?: emptyList(),
    precip = precip,
    pressure = pressure
)

fun HourlyForecastEntity.toHDomainModel(): HourlyModel {
    return HourlyModel(
        time = time,
        temperature = temperature,
        windSpeed = windSpeed,
        weatherIcons = listOf(weatherIconUrl),
        weatherDescriptions = listOf(weatherDescription),
        precip = precip,
        pressure = pressure
    )
}

private fun CurrentLocationResponse.toDomainModel(): CurrentLocationModel = CurrentLocationModel(
    request = request,
    location = location,
    current = current
)

fun CurrentWeatherEntity.toCDomainModel(): CurrentLocationModel {
    return CurrentLocationModel(
        location = Location(
            name = locationName,
            country = country,
            region = region
        ),
        current = Current(
            temperature = temperature,
            weatherDescriptions = listOf(weatherDescription),
            weatherIcons = listOf(weatherIconUrl),
            windSpeed = windSpeed,
            pressure = pressure,
            precip = precip,
            observationTime = observationTime
        ),
        request = null
    )
}

fun CurrentLocationModel.toCurrentEntity(): CurrentWeatherEntity {
    return CurrentWeatherEntity(
        locationName = location?.name ?: "",
        country = location?.country ?: "",
        region = location?.region ?: "",
        temperature = current?.temperature ?: 0,
        weatherDescription = current?.weatherDescriptions?.firstOrNull() ?: "",
        weatherIconUrl = current?.weatherIcons?.firstOrNull() ?: "",
        windSpeed = current?.windSpeed ?: 0,
        pressure = current?.pressure ?: 0,
        precip = current?.precip ?: 0.0,
        observationTime = current?.observationTime ?: ""
    )
}

fun HourlyModel.toHourlyEntity(locationName: String): HourlyForecastEntity {
    return HourlyForecastEntity(
        locationName = locationName,
        time = time ?: "",
        temperature = temperature ?: 0,
        weatherDescription = weatherDescriptions?.firstOrNull() ?: "",
        weatherIconUrl = weatherIcons.firstOrNull() ?: "",
        windSpeed = windSpeed ?: 0,
        pressure = pressure ?: 0,
        precip = precip ?: 0.0
    )
}

