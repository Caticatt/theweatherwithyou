package com.alexcfa.yourweather.ui.screens.current

import CurrentLocationResponse
import android.Manifest
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.alexcfa.yourweather.R
import com.alexcfa.yourweather.ui.common.LoadingProgressIndicator
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
                LoadingProgressIndicator(modifier = modifier.padding(padding))
            }

            state.currentLocationResponse?.let { currentLocationResponse ->
                CurrentLocationExtraction(
                    currentLocationResponse = currentLocationResponse,
                    modifier = Modifier.padding(padding),
                    onHourlyClick
                )
            }

        }

    }
}

@Composable
private fun CurrentLocationExtraction(
    currentLocationResponse: CurrentLocationResponse,
    modifier: Modifier = Modifier,
    onHourlyClick: () -> Unit
) {
    Column(
        modifier = Modifier
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
                text = "${currentLocationResponse.location.name}, ${currentLocationResponse.location.country}  ${currentLocationResponse.current.observationTime}",
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
                    model = currentLocationResponse.current.weatherIcons[0],
                    contentDescription = "timeimage",
                    modifier = modifier
                        .height(200.dp)
                        .clip(MaterialTheme.shapes.small)
                )
                Text(
                    text = currentLocationResponse.current.weatherDescriptions[0],
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
                    text = "${currentLocationResponse.current.temperature}ºC",
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
                text = "Viento: ${currentLocationResponse.current.windSpeed} kmph",
                style = MaterialTheme.typography.titleLarge,
                modifier = modifier
            )
            Text(
                text = "Precipitación: ${currentLocationResponse.current.precip} mm",
                style = MaterialTheme.typography.titleLarge,
                modifier = modifier
            )
            Text(
                text = "Presión: ${currentLocationResponse.current.pressure} mb",
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

@Preview
@Composable
fun CurrentScreenPreview() {
    CurrentScreen(
        onHourlyClick = {}
    )
}

