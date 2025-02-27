package com.alexcfa.yourweather.ui.screens.hourly

import Hourly
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexcfa.yourweather.data.WeatherRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HourlyViewModel : ViewModel() {

    private val repository = WeatherRepository()

    private val _state = MutableStateFlow(UiState())
    val state: StateFlow<UiState> get() = _state.asStateFlow()

    @RequiresApi(Build.VERSION_CODES.O)
    fun onUiReady() {
        viewModelScope.launch {
            _state.value = UiState(loading = true)
            _state.value = UiState(loading = false, hourly = repository.fetchHourlyLocationData())
        }
    }

    data class UiState(
        val loading: Boolean = false,
        val hourly: List<Hourly> = emptyList(),
        val message: String? = null
    )

    fun onRefreshClick() {
        _state.update { it.copy(message = "Tiempo actualizado") }
    }

    fun onMessageShown() {
        _state.update { it.copy(message = null) }
    }

}
