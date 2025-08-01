package com.alexcfa.yourweather.data.datasource

import android.os.Build
import androidx.annotation.RequiresApi
import com.alexcfa.yourweather.domain.CurrentLocationModel
import com.alexcfa.yourweather.domain.HourlyModel
import com.alexcfa.yourweather.ui.common.getActualDate

const val UNITS = "m"
const val INTERVAL = 1
const val HOURLY = 1

interface WeatherRemoteDataSource {
    suspend fun fetchCurrentLocation(query: String, units: String = UNITS): CurrentLocationModel

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun fetchHourlyLocationData(
        query: String,
        historicalDate: String = getActualDate(),
        hourly: Int = HOURLY,
        interval: Int = INTERVAL,
        units: String = UNITS
    ): List<HourlyModel>?
}


