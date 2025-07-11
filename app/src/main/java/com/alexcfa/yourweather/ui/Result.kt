package com.alexcfa.yourweather.ui

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

sealed interface Result<out T> {
    data class Success<T>(val value: T) : Result<T>
    data class Error(val error: Throwable) : Result<Nothing>
    data object Loading : Result<Nothing>
}

inline fun <T>Result<T>.ifSuccess(block: (T) -> Unit){
    if(this is Result.Success) block(value)
}

fun <T> Flow<T>.stateAsResultIn(scope: CoroutineScope): Flow<Result<T>> =
    map<T, Result<T>> { Result.Success(it) }
        .catch { emit(Result.Error(it)) }
        .stateIn(
            scope = scope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = Result.Loading
        )

