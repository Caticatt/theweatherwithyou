package com.alexcfa.yourweather.data.datasource

import android.os.Build
import androidx.annotation.RequiresApi
import com.alexcfa.yourweather.domain.CurrentLocationModel
import com.alexcfa.yourweather.domain.HourlyModel
import com.alexcfa.yourweather.domain.constants.DefaultLocationConstants.HOURLY
import com.alexcfa.yourweather.domain.constants.DefaultLocationConstants.INTERVAL
import com.alexcfa.yourweather.domain.constants.DefaultLocationConstants.UNITS
import com.alexcfa.yourweather.ui.common.getActualDate

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


