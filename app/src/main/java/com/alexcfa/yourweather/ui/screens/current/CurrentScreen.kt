package com.alexcfa.yourweather.ui.screens.current

import android.Manifest
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.alexcfa.yourweather.R
import com.alexcfa.yourweather.ui.common.PermissionRequestEffect
import com.alexcfa.yourweather.ui.common.getRegion
import com.alexcfa.yourweather.ui.theme.YourWeatherTheme
import kotlinx.coroutines.launch

@Composable
fun Screen(content: @Composable () -> Unit) {
    YourWeatherTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background,
            content = content
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CurrentScreen(
    onHourlyClick: () -> Unit,
    vm: CurrentViewModel = viewModel(),
    modifier: Modifier = Modifier,
) {
    val ctx = LocalContext.current
    val appName = stringResource(id = R.string.app_name)
    var appBarTitle by remember { mutableStateOf(appName) }
    val coroutineScope = rememberCoroutineScope()

    PermissionRequestEffect(permission = Manifest.permission.ACCESS_COARSE_LOCATION) { granted ->
        if (granted) {
            coroutineScope.launch {
                val region = ctx.getRegion()
                appBarTitle = "$appName EN ${region.uppercase()}"
            }
        } else {
            appBarTitle = "$appName (Permission denied)"
        }
        vm.onUiReady()
    }
    Screen {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(text = appBarTitle) }
                )
            }
        ) { padding ->
            val state = vm.state

            if (state.loading) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            Column(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.inversePrimary),
            ) {

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "${state.currentLocationResponse?.location?.name}, ${state.currentLocationResponse?.location?.country}  ${state.currentLocationResponse?.current?.observationTime.toString()}",
                        style = MaterialTheme.typography.headlineLarge
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f)
                        .padding(16.dp)
                        .border(2.dp, Color.Black, RoundedCornerShape(4.dp))
                        .background(MaterialTheme.colorScheme.onPrimary, RoundedCornerShape(4.dp)),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {

                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxSize()
                            .padding(8.dp),
                        verticalArrangement = Arrangement.SpaceBetween,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        AsyncImage(
                            model = state.currentLocationResponse?.current?.weatherIcons?.get(0),
                            contentDescription = "timeimage",
                            modifier = modifier
                                .height(200.dp)
                                .clip(MaterialTheme.shapes.small)
                        )
                        Text(
                            text = state.currentLocationResponse?.current?.weatherDescriptions?.get(0)
                                .toString(),
                            style = MaterialTheme.typography.titleLarge,
                            modifier = modifier.align(Alignment.CenterHorizontally)
                        )
                    }

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .weight(1f),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "${state.currentLocationResponse?.current?.temperature.toString()}ºC",
                            style = MaterialTheme.typography.displayLarge,
                            modifier = modifier.align(Alignment.CenterHorizontally)
                        )
                    }
                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(16.dp),
                    verticalArrangement = Arrangement.SpaceBetween,
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = "Viento: ${state.currentLocationResponse?.current?.windSpeed} kmph",
                        style = MaterialTheme.typography.titleLarge,
                        modifier = modifier
                    )
                    Text(
                        text = "Precipitación: ${state.currentLocationResponse?.current?.precip} mm",
                        style = MaterialTheme.typography.titleLarge,
                        modifier = modifier
                    )
                    Text(
                        text = "Presión: ${state.currentLocationResponse?.current?.pressure} mb",
                        style = MaterialTheme.typography.titleLarge,
                        modifier = modifier
                    )
                }

                Button(
                    onClick = { onHourlyClick() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(text = "Ver tiempo durante el día")
                }

            }
        }

    }
}

