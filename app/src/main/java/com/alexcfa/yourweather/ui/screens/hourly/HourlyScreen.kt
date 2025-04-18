package com.alexcfa.yourweather.ui.screens.hourly

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.alexcfa.yourweather.R
import com.alexcfa.yourweather.data.HourlyModel
import com.alexcfa.yourweather.ui.common.LoadingProgressIndicator
import com.alexcfa.yourweather.ui.common.getActualDateString
import com.alexcfa.yourweather.ui.screens.Screen


@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HourlyScreen(
    onBack: () -> Unit,
    viewModel: HourlyViewModel = viewModel()
) {
    Screen {

        val hourlyState = rememberHourlyState()
        val state by viewModel.state.collectAsState()

        hourlyState.ShowMessageEffect(message = state.message) {
            viewModel.onMessageShown()
        }

        Scaffold(
            topBar = {
                val topBarTitle = stringResource(id = R.string.hourly_weather_tittle)
                HourlyTopBar(
                    title = topBarTitle,
                    scrollBehavior = hourlyState.scrollBehavior,
                    onBack = onBack,
                )
            },
            floatingActionButton = {
                FloatingActionButton(onClick = { viewModel.onRefreshClick() }) {
                    Icon(
                        imageVector = Icons.Default.Refresh,
                        contentDescription = stringResource(id = R.string.back)
                    )
                }
            },
            snackbarHost = {
                SnackbarHost(hostState = hourlyState.snackBarHostState)
            },
            modifier = Modifier
                .nestedScroll(hourlyState.scrollBehavior.nestedScrollConnection)
                .background(MaterialTheme.colorScheme.inversePrimary),
            contentWindowInsets = WindowInsets.safeDrawing
        ) { padding ->

            if (state.loading) {
                LoadingProgressIndicator(modifier = Modifier.padding(padding))
            }

            LazyColumn(
                contentPadding = padding
            ) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(40.dp)
                            .background(MaterialTheme.colorScheme.inversePrimary)
                    ) {
                        Text(
                            text = getActualDateString(),
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                }
                val hourlyList = state.hourly
                itemsIndexed(hourlyList) { index, hourly ->
                    WeatherItem(hourly)
                }
              /*  items(state.hourly, key = { it.time.toString() }) {
                    WeatherItem(hourly = it)
                }*/
            }
            viewModel.onUiReady()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HourlyTopBar(
    title: String,
    scrollBehavior: TopAppBarScrollBehavior,
    onBack: () -> Unit,
) {
    TopAppBar(
        title = { Text(text = title) },
        navigationIcon = {
            IconButton(onClick = onBack) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = stringResource(id = R.string.back),
                )
            }

        },
        scrollBehavior = scrollBehavior
    )
}

@Composable
fun WeatherItem(hourly: HourlyModel) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = hourly.time.toString(),
            style = MaterialTheme.typography.bodySmall,
            maxLines = 1,
            modifier = Modifier
        )
        Column {
            AsyncImage(
                model = hourly.weatherIcons[0],
                contentDescription = "weatherIcon",
                modifier = Modifier
                    .height(40.dp)
                    .width(40.dp)
                    .background(Color.Gray)
                    .clip(MaterialTheme.shapes.extraSmall)
            )
            Text(
                text = hourly.weatherDescriptions?.get(0).toString(),
                style = MaterialTheme.typography.bodySmall,
                maxLines = 1,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }
        Text(
            text = hourly.temperature.toString(),
            style = MaterialTheme.typography.bodySmall,
            maxLines = 1,
            modifier = Modifier
        )
        Text(
            text = hourly.windSpeed.toString(),
            style = MaterialTheme.typography.bodySmall,
            maxLines = 1,
            modifier = Modifier
        )
        Text(
            text = hourly.precip.toString(),
            style = MaterialTheme.typography.bodySmall,
            maxLines = 1,
            modifier = Modifier
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun HourlyScreenPreview() {
    HourlyScreen(
        onBack = {}
    )
}