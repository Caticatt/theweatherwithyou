package com.alexcfa.yourweather.usecases

import com.alexcfa.yourweather.data.CurrentLocationModel
import com.alexcfa.yourweather.data.WeatherRepository
import kotlinx.coroutines.flow.Flow

class FetchCurrentWeatherUseCase (private val repository: WeatherRepository){
    operator fun invoke() : Flow<CurrentLocationModel> = repository.getCurrentWeatherFlow()
}