package com.alexcfa.yourweather.ui.screens.hourly

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexcfa.yourweather.data.HourlyModel
import com.alexcfa.yourweather.ui.Result
import com.alexcfa.yourweather.ui.stateAsResultIn
import com.alexcfa.yourweather.usecases.FetchHourlyForecastUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class HourlyViewModel(private val fetchHourlyForecastUseCase: FetchHourlyForecastUseCase) : ViewModel() {

    @OptIn(ExperimentalCoroutinesApi::class)
    @RequiresApi(Build.VERSION_CODES.O)
    private val hourlyForecastFlow = flow {
        emit(fetchHourlyForecastUseCase())
    }.flatMapConcat { it }

    @RequiresApi(Build.VERSION_CODES.O)
    val state: StateFlow<UiState> = hourlyForecastFlow
        .stateAsResultIn(viewModelScope)
        .map { result ->
            when (result) {
                is Result.Loading -> UiState(loading = true)
                is Result.Success -> UiState(loading = false, hourly = result.value)
                is Result.Error -> UiState(loading = false)
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = UiState(loading = true)
        )

    data class UiState(
        val loading: Boolean = false,
        val hourly: List<HourlyModel> = emptyList(),
    )

    @RequiresApi(Build.VERSION_CODES.O)
    fun onRefreshClick() {
        viewModelScope.launch {
            fetchHourlyForecastUseCase
        }
    }
}
