package com.alexcfa.yourweather.ui.screens.hourly

import Hourly
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexcfa.yourweather.data.WeatherRepository
import kotlinx.coroutines.launch

class HourlyViewModel : ViewModel() {

    private val repository = WeatherRepository()

    var state by mutableStateOf(UiState())
        private set

    @RequiresApi(Build.VERSION_CODES.O)
    fun onUiReady() {
        viewModelScope.launch {
            state = UiState(loading = true)
            state = UiState(loading = false, hourly = repository.fetchHourlyLocationData())
        }
    }

    data class UiState(
        val loading: Boolean = false,
        val hourly: List<Hourly> = emptyList()
    )

}
