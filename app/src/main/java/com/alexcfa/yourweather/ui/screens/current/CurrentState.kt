package com.alexcfa.yourweather.ui.screens.current

import android.Manifest
import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import com.alexcfa.yourweather.R
import com.alexcfa.yourweather.ui.common.PermissionRequestEffect
import com.alexcfa.yourweather.ui.common.getRegion
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class CurrentState(private val context: Context, private val coroutineScope: CoroutineScope) {

    var appBarTitle by mutableStateOf(context.getString(R.string.app_name))
        private set

    private fun askRegion(onRegion: (String) -> Unit) {
        coroutineScope.launch {
            val region = context.getRegion()
            appBarTitle = "${context.getString(R.string.app_name)} EN ${region.uppercase()}"
            onRegion(region)
        }
    }

    private fun onPermissionDenied() {
        appBarTitle = "${context.getString(R.string.app_name)} ES"
    }

    @Composable
    fun AskRegionEffect(currentState: CurrentState, onRegion: (String) -> Unit) {
        PermissionRequestEffect(permission = Manifest.permission.ACCESS_COARSE_LOCATION) { granted ->
            coroutineScope.launch {
                if (granted) currentState.askRegion(onRegion) else onPermissionDenied()
            }
        }
    }
}

@Composable
fun rememberCurrentState(
    context: Context = LocalContext.current,
    coroutineScope: CoroutineScope = rememberCoroutineScope()
): CurrentState {
    return remember(context, coroutineScope) { CurrentState(context, coroutineScope) }
}