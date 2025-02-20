package com.alexcfa.yourweather.ui.screens.current

import CurrentLocationResponse
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexcfa.yourweather.data.WeatherRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CurrentViewModel : ViewModel() {

    private val repository = WeatherRepository()

    private val _state = MutableStateFlow(UiState())
    val state: StateFlow<UiState> get() = _state.asStateFlow()

    fun onUiReady() {
        viewModelScope.launch {
            _state.value = UiState(loading = true)
            _state.value = UiState(
                loading = false,
                currentLocationResponse = repository.fetchCurrentLocation()
            )
        }
    }

    data class UiState(
        val loading: Boolean = false,
        val currentLocationResponse: CurrentLocationResponse? = null
    )

}
