package com.alexcfa.yourweather.ui.screens.current

import CurrentLocationResponse
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexcfa.yourweather.data.WeatherRepository
import kotlinx.coroutines.launch

class CurrentViewModel : ViewModel() {

    var state by mutableStateOf(UiState())
        private set

    private val repository = WeatherRepository()

    fun onUiReady() {
        viewModelScope.launch {
            state = UiState(loading = true)
            state = UiState(loading = false, currentLocationResponse = repository.fetchCurrentLocation())
        }
    }

    data class UiState(
        val loading: Boolean = false,
        val currentLocationResponse: CurrentLocationResponse? = null
    )

}