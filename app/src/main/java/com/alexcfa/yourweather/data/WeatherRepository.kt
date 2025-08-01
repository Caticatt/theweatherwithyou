package com.alexcfa.yourweather.data

import android.os.Build
import androidx.annotation.RequiresApi
import com.alexcfa.yourweather.domain.CurrentLocationModel
import com.alexcfa.yourweather.domain.CurrentModel
import com.alexcfa.yourweather.domain.HourlyModel
import com.alexcfa.yourweather.domain.LocationModel
import com.alexcfa.yourweather.framework.database.DbCurrentWeather
import com.alexcfa.yourweather.framework.database.DbHourlyForecast
import com.alexcfa.yourweather.framework.WeatherRoomDataSource
import com.alexcfa.yourweather.framework.remote.WeatherServerDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.transform
import kotlin.String

class WeatherRepository(
    private val regionRepository: RegionRepository,
    private val localDataSource: WeatherRoomDataSource,
    private val remoteDataSource: WeatherServerDataSource
) {

    fun getCurrentWeatherFlow(): Flow<CurrentLocationModel> =
        localDataSource.lastCurrentWeather.transform { currentWeather ->
            if (currentWeather == null) {
                val region = regionRepository.findLastRegionComplete()
                val remoteData = remoteDataSource.fetchCurrentLocation(region)
                localDataSource.saveCurrentWeather(remoteData.toCurrentEntity())
                emit(remoteData)
            } else {
                emit(currentWeather.toDomainCurrentModel())
            }
        }


    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun getHourlyForecastFlow(): Flow<List<HourlyModel>> =
        localDataSource.getHourlyForecastsByLocation(regionRepository.findLastRegionComplete())
            .transform { forecasts ->
                if (forecasts.isEmpty()) {
                    val region = regionRepository.findLastRegionComplete()
                    val remoteData = remoteDataSource.fetchHourlyLocationData(region)
                    remoteData?.let { data ->
                        localDataSource.deleteHourlyForecastsByLocation(region)
                        localDataSource.saveHourlyForecasts(data.map { it.toHourlyEntity(region) })
                        emit(data)
                    }
                } else {
                    emit(forecasts.map { it.toDomainHourlyModel() })
                }
            }

}

fun DbCurrentWeather.toDomainCurrentModel() = CurrentLocationModel(
    request = null,
    location = LocationModel(
        name = locationName,
        country = country,
        region = region,
        lat = null,
        lon = null,
        timezone_id = null,
        localtime = null,
        localtime_epoch = null,
        utc_offset = null
    ),
    current = CurrentModel(
        observation_time = observationTime,
        temperature = temperature,
        weather_code = null,
        weather_icons = listOf(weatherIconUrl),
        weather_descriptions = listOf(weatherDescription),
        wind_speed = windSpeed,
        wind_degree = null,
        wind_dir = null,
        pressure = pressure,
        precip = precip,
        humidity = null,
        cloudcover = null,
        feelslike = null,
        uv_index = null,
        visibility = null,
        is_day = null
    ),
)

fun DbHourlyForecast.toDomainHourlyModel(): HourlyModel = HourlyModel(
    time = time,
    temperature = temperature,
    windSpeed = windSpeed,
    weatherIcons = listOf(weatherIconUrl),
    weatherDescriptions = listOf(weatherDescription),
    precip = precip,
    pressure = pressure
)


fun CurrentLocationModel.toCurrentEntity(): DbCurrentWeather {
    return DbCurrentWeather(
        locationName = location?.name ?: "",
        country = location?.country ?: "",
        region = location?.region ?: "",
        temperature = current?.temperature ?: 0,
        weatherDescription = current?.weather_descriptions?.firstOrNull() ?: "",
        weatherIconUrl = current?.weather_icons?.firstOrNull() ?: "",
        windSpeed = current?.wind_speed ?: 0,
        pressure = current?.pressure ?: 0,
        precip = current?.precip ?: 0.0,
        observationTime = current?.observation_time ?: ""
    )
}

fun HourlyModel.toHourlyEntity(locationName: String): DbHourlyForecast {
    return DbHourlyForecast(
        compositeKey = "${locationName}_${time}",
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
