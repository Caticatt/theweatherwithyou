package com.alexcfa.yourweather.ui.screens.current

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexcfa.yourweather.domain.CurrentLocationModel
import com.alexcfa.yourweather.ui.Result
import com.alexcfa.yourweather.ui.ifSuccess
import com.alexcfa.yourweather.ui.stateAsResultIn
import com.alexcfa.yourweather.usecases.FetchCurrentWeatherUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class CurrentViewModel(private val fetchCurrentWeatherUseCase: FetchCurrentWeatherUseCase) : ViewModel() {

    val state: StateFlow<Result<CurrentLocationModel>> = fetchCurrentWeatherUseCase()
        .stateAsResultIn(viewModelScope)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = Result.Loading
        )

    fun reloadData() {
        viewModelScope.launch {
            fetchCurrentWeatherUseCase()
        }
    }

    fun onUiReady() {
        state.value.ifSuccess {
            if (state.value is Result.Success) {
                reloadData()
            }
        }
    }

}
