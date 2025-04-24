package com.alexcfa.yourweather.ui.screens.current

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
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.alexcfa.yourweather.data.CurrentLocationModel
import com.alexcfa.yourweather.ui.common.LoadingProgressIndicator
import com.alexcfa.yourweather.ui.common.PermissionRequestEffect
import com.alexcfa.yourweather.ui.screens.Screen


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CurrentScreen(
    onHourlyClick: () -> Unit,
    viewModel: CurrentViewModel ,
    modifier: Modifier = Modifier,
) {

    val currentState = rememberCurrentState()
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.reloadData()
    }

    PermissionRequestEffect(Manifest.permission.ACCESS_COARSE_LOCATION) {
        viewModel.onUiReady()
    }

    Screen {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(text = currentState.appBarTitle) }
                )
            }
        ) { padding ->

            if (state.loading) {
                LoadingProgressIndicator(modifier = modifier.padding(padding))
            }

            state.currentLocation?.let { currentLocation ->
                CurrentLocationExtraction(
                    currentLocation = currentLocation,
                    modifier = Modifier.padding(padding),
                    onHourlyClick
                )
            }

        }

    }
}

@Composable
private fun CurrentLocationExtraction(
    currentLocation: CurrentLocationModel,
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
                text = "${currentLocation.location?.name}, ${currentLocation.location?.country}  ${currentLocation.current?.observationTime}",
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
                    model = currentLocation.current?.weatherIcons?.get(0),
                    contentDescription = "timeimage",
                    modifier = modifier
                        .height(200.dp)
                        .clip(MaterialTheme.shapes.small)
                )
                Text(
                    text = currentLocation.current?.weatherDescriptions?.get(0).toString(),
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
                    text = "${currentLocation.current?.temperature}ºC",
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
                text = "Viento: ${currentLocation.current?.windSpeed} kmph",
                style = MaterialTheme.typography.titleLarge,
                modifier = modifier
            )
            Text(
                text = "Precipitación: ${currentLocation.current?.precip} mm",
                style = MaterialTheme.typography.titleLarge,
                modifier = modifier
            )
            Text(
                text = "Presión: ${currentLocation.current?.pressure} mb",
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



