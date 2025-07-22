package com.alexcfa.yourweather.usecases

import com.alexcfa.yourweather.data.WeatherRepository
import com.alexcfa.yourweather.domain.CurrentLocationModel
import kotlinx.coroutines.flow.Flow

class FetchCurrentWeatherUseCase (private val repository: WeatherRepository){
    operator fun invoke() : Flow<CurrentLocationModel> = repository.getCurrentWeatherFlow()
}