package com.alexcfa.yourweather.ui.screens.hourly

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember

class HourlyState @OptIn(ExperimentalMaterial3Api::class) constructor(
    val scrollBehavior: TopAppBarScrollBehavior,
    val snackBarHostState: SnackbarHostState
){

    @Composable
    fun ShowMessageEffect(message: String?, onMessageShown: () -> Unit){
        LaunchedEffect(message) {
            message?.let {
               snackBarHostState.currentSnackbarData?.dismiss()
                snackBarHostState.showSnackbar(it)
               onMessageShown()
            }

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun rememberHourlyState(
    scrollBehavior: TopAppBarScrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(),
    snackBarHostState: SnackbarHostState = remember { SnackbarHostState() }
): HourlyState {
    return remember(scrollBehavior, snackBarHostState) { HourlyState(scrollBehavior, snackBarHostState) }
}