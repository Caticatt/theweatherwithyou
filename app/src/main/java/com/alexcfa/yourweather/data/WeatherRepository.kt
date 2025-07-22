package com.alexcfa.yourweather.data

import android.os.Build
import androidx.annotation.RequiresApi
import com.alexcfa.yourweather.data.database.DbCurrentWeather
import com.alexcfa.yourweather.data.database.DbHourlyForecast
import com.alexcfa.yourweather.data.datasource.WeatherServerDataSource
import com.alexcfa.yourweather.data.remote.RemoteCurrent
import com.alexcfa.yourweather.data.remote.RemoteLocation
import com.alexcfa.yourweather.domain.CurrentLocationModel
import com.alexcfa.yourweather.domain.HourlyModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.transform

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
    location = RemoteLocation(
        name = locationName,
        country = country,
        region = region
    ),
    current = RemoteCurrent(
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
        weatherDescription = current?.weatherDescriptions?.firstOrNull() ?: "",
        weatherIconUrl = current?.weatherIcons?.firstOrNull() ?: "",
        windSpeed = current?.windSpeed ?: 0,
        pressure = current?.pressure ?: 0,
        precip = current?.precip ?: 0.0,
        observationTime = current?.observationTime ?: ""
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
