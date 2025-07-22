package com.alexcfa.yourweather.ui.screens

import android.location.Geocoder
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.alexcfa.yourweather.App
import com.alexcfa.yourweather.data.RegionRepository
import com.alexcfa.yourweather.data.WeatherRepository
import com.alexcfa.yourweather.data.WeatherRoomDataSource
import com.alexcfa.yourweather.data.datasource.GeocoderRegionDataSource
import com.alexcfa.yourweather.data.datasource.PlayServicesLocationDataSource
import com.alexcfa.yourweather.data.datasource.WeatherServerDataSource
import com.alexcfa.yourweather.data.remote.WeatherClient
import com.alexcfa.yourweather.ui.screens.current.CurrentScreen
import com.alexcfa.yourweather.ui.screens.current.CurrentViewModel
import com.alexcfa.yourweather.ui.screens.hourly.HourlyScreen
import com.alexcfa.yourweather.ui.screens.hourly.HourlyViewModel
import com.alexcfa.yourweather.usecases.FetchCurrentWeatherUseCase
import com.alexcfa.yourweather.usecases.FetchHourlyForecastUseCase
import com.google.android.gms.location.LocationServices

sealed class NavScreen(val route: String) {
    data object CurrentScreen : NavScreen("currentScreen")
    data object HourlyScreen : NavScreen("hourlyScreen")
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Navigation() {
    val navController = rememberNavController()
    val app = LocalContext.current.applicationContext as App

    val weatherRepository = remember {
        WeatherRepository(
            RegionRepository(
                GeocoderRegionDataSource(
                    Geocoder(app),
                    PlayServicesLocationDataSource(
                        LocationServices.getFusedLocationProviderClient(
                            app
                        )
                    )
                )
            ),
            WeatherRoomDataSource(
                app.database.currentWeatherDao(),
                app.database.hourlyForecastDao()
            ),
            WeatherServerDataSource(WeatherClient.instance)
        )
    }

    val currentViewModel = CurrentViewModel(FetchCurrentWeatherUseCase(weatherRepository))
    val hourlyViewModel = HourlyViewModel(FetchHourlyForecastUseCase(weatherRepository))

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
