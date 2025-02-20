package com.alexcfa.yourweather.ui.screens.hourly

import Hourly
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
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
import com.alexcfa.yourweather.ui.common.LoadingProgressIndicator
import com.alexcfa.yourweather.ui.screens.Screen


@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HourlyScreen(
    onBack: () -> Unit,
    viewModel: HourlyViewModel = viewModel()
) {
    Screen {
        val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
        Scaffold(
            topBar = {
                HourlyTopBar(
                    stringResource(R.string.top_bar_tittle),
                    onBack,
                    scrollBehavior
                )
            },
            modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
            contentWindowInsets = WindowInsets.safeDrawing
        ) { padding ->
            val state = viewModel.state

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
                            .height(60.dp)
                            .background(MaterialTheme.colorScheme.inversePrimary)
                    ) {
                        Text(text = "Martes, 3 Diciembre")
                        Text(
                            text = "Tiempo en horas",
                            modifier = Modifier.align(Alignment.BottomStart)
                        )
                    }
                }
                items(state.hourly, key = { it.time }) {
                    WeatherItem(hourly = it)
                }
            }
            viewModel.onUiReady()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HourlyTopBar(
    title: String,
    onBack: () -> Unit,
    scrollBehavior: TopAppBarScrollBehavior
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
fun WeatherItem(hourly: Hourly) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = hourly.time,
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
                text = hourly.weatherDescriptions[0],
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