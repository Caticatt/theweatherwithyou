package com.alexcfa.yourweather.data

import android.os.Build
import androidx.annotation.RequiresApi
import com.alexcfa.yourweather.data.datasource.WeatherRemoteDataSource
import com.alexcfa.yourweather.data.datasource.toCDomainModel
import com.alexcfa.yourweather.data.datasource.toCurrentEntity
import com.alexcfa.yourweather.data.datasource.toHDomainModel
import com.alexcfa.yourweather.data.datasource.toHourlyEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.transform

class WeatherRepository(
    private val regionRepository: RegionRepository,
    private val localDataSource: WeatherLocalDataSource,
    private val remoteDataSource: WeatherRemoteDataSource
) {

    fun getCurrentWeatherFlow(): Flow<CurrentLocationModel> =
        localDataSource.lastCurrentWeather.transform { currentWeather ->
            if (currentWeather == null) {
                val region = regionRepository.findLastRegionComplete()
                val remoteData = remoteDataSource.fetchCurrentLocation(region)
                localDataSource.saveCurrentWeather(remoteData.toCurrentEntity())
                emit(remoteData)
            } else {
                emit(currentWeather.toCDomainModel())
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
                    emit(forecasts.map { it.toHDomainModel() })
                }
            }

}
