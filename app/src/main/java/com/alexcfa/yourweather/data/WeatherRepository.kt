package com.alexcfa.yourweather.data

import android.os.Build
import androidx.annotation.RequiresApi
import com.alexcfa.yourweather.data.datasource.WeatherRemoteDataSource
import com.alexcfa.yourweather.data.datasource.toCDomainModel
import com.alexcfa.yourweather.data.datasource.toCurrentEntity
import com.alexcfa.yourweather.data.datasource.toHDomainModel
import com.alexcfa.yourweather.data.datasource.toHourlyEntity

class WeatherRepository(
    private val regionRepository: RegionRepository,
    private val localDataSource: WeatherLocalDataSource,
    private val remoteDataSource: WeatherRemoteDataSource
) {


    suspend fun fetchCurrentLocation(): CurrentLocationModel {
        if (localDataSource.getLastCurrentWeather() == null) {
            val region = regionRepository.findLastRegionComplete()
            val currentWeatherData = remoteDataSource.fetchCurrentLocation(region)
            localDataSource.saveCurrentWeather(currentWeatherData.toCurrentEntity())
        }
        return checkNotNull(localDataSource.getLastCurrentWeather()?.toCDomainModel())
    }

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun fetchHourlyLocationData(): List<HourlyModel> {
        val region = regionRepository.findLastRegionComplete()
        localDataSource.deleteHourlyForecastsByLocation(region)
        val hourlyWeatherData = remoteDataSource.fetchHourlyLocationData(region)
        hourlyWeatherData?.let { data ->
            localDataSource.saveHourlyForecasts(
                data.map { it.toHourlyEntity(region) }
            )
        }
        return localDataSource.getHourlyForecastsByLocation(region)
            .map { it.toHDomainModel() }
    }

}
