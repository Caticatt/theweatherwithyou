package com.alexcfa.yourweather.ui.screens.current

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import com.alexcfa.yourweather.R

class CurrentState(context: Context) {

    var appBarTitle by mutableStateOf(context.getString(R.string.app_name))
        private set

}

@Composable
fun rememberCurrentState(
    context: Context = LocalContext.current,
): CurrentState {
    return remember(context) { CurrentState(context) }
}