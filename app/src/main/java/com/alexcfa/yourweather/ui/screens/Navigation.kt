package com.alexcfa.yourweather.ui.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.alexcfa.yourweather.App
import com.alexcfa.yourweather.data.RegionRepository
import com.alexcfa.yourweather.data.WeatherLocalDataSource
import com.alexcfa.yourweather.data.WeatherRepository
import com.alexcfa.yourweather.data.datasource.LocationDatasource
import com.alexcfa.yourweather.data.datasource.RegionDataSource
import com.alexcfa.yourweather.data.datasource.WeatherRemoteDataSource
import com.alexcfa.yourweather.ui.screens.current.CurrentScreen
import com.alexcfa.yourweather.ui.screens.current.CurrentViewModel
import com.alexcfa.yourweather.ui.screens.hourly.HourlyScreen
import com.alexcfa.yourweather.ui.screens.hourly.HourlyViewModel


sealed class NavScreen(val route: String) {
    data object CurrentScreen : NavScreen("currentScreen")
    data object HourlyScreen : NavScreen("hourlyScreen")
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Navigation() {
    val navController = rememberNavController()
    val app = LocalContext.current.applicationContext as App

    val weatherRepository = WeatherRepository(
        RegionRepository(RegionDataSource(app, LocationDatasource(app))),
        WeatherLocalDataSource(app.database.currentWeatherDao(), app.database.hourlyForecastDao()),
        WeatherRemoteDataSource()
    )

    val currentViewModel = CurrentViewModel(weatherRepository)
    val hourlyViewModel = HourlyViewModel(weatherRepository)

    NavHost(navController = navController, startDestination = NavScreen.CurrentScreen.route) {
        composable(NavScreen.CurrentScreen.route) {
            CurrentScreen(
                onHourlyClick = { navController.navigate(NavScreen.HourlyScreen.route) },
                viewModel = currentViewModel
            )
        }
        composable(NavScreen.HourlyScreen.route) {
            HourlyScreen(
                viewModel = hourlyViewModel,
                onBack = { navController.popBackStack() }
            )
        }
    }
}
