package com.alexcfa.yourweather.usecases

import android.os.Build
import androidx.annotation.RequiresApi
import com.alexcfa.yourweather.data.HourlyModel
import com.alexcfa.yourweather.data.WeatherRepository
import kotlinx.coroutines.flow.Flow

class FetchHourlyForecastUseCase ( private val repository: WeatherRepository){
    @RequiresApi(Build.VERSION_CODES.O)
    suspend operator fun invoke() : Flow<List<HourlyModel>> = repository.getHourlyForecastFlow()
}