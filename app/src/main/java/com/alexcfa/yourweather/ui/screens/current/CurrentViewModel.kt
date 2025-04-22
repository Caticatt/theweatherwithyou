package com.alexcfa.yourweather.ui.screens.current

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexcfa.yourweather.data.CurrentLocationModel
import com.alexcfa.yourweather.data.WeatherRepository
import com.alexcfa.yourweather.data.datasource.toCurrentEntity
import com.alexcfa.yourweather.ui.screens.hourly.HourlyViewModel.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CurrentViewModel(
    private val repository: WeatherRepository
) : ViewModel() {

    private val _state = MutableStateFlow(UiState())
    val state: StateFlow<UiState> get() = _state.asStateFlow()

    fun reloadData() {
        viewModelScope.launch {
            _state.value = UiState(loading = true)
            _state.value = UiState(
                loading = false,
                currentLocation = repository.fetchCurrentLocation()
            )
        }
    }

    fun onUiReady() {
        if (_state.value.currentLocation == null) {
            reloadData()
        }
    }
    /*viewModelScope.launch {
        _state.value = UiState(loading = true)
        _state.value = UiState(
            loading = false,
            currentLocation = repository.fetchCurrentLocation()
        )
    }*/

    data class UiState(
        val loading: Boolean = false,
        val currentLocation: CurrentLocationModel? = null
    )

}
