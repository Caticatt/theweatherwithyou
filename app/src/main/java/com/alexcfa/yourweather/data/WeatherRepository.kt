package com.alexcfa.yourweather.data

import android.os.Build
import androidx.annotation.RequiresApi
import com.alexcfa.yourweather.data.datasource.WeatherRemoteDataSource


class WeatherRepository(
    private val regionRepository: RegionRepository,
    private val remoteDataSource: WeatherRemoteDataSource = WeatherRemoteDataSource()
) {

    suspend fun fetchCurrentLocation(): CurrentLocationModel? =
        remoteDataSource.fetchCurrentLocation(
            regionRepository.findLastRegionComplete()
        )

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun fetchHourlyLocationData(): List<HourlyModel>? =
        remoteDataSource.fetchHourlyLocationData(
            regionRepository.findLastRegionComplete()
        )

}
