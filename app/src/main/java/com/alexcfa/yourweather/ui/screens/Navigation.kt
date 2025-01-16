package com.alexcfa.yourweather.ui.screens

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.alexcfa.yourweather.ui.screens.hourly.HourlyScreen
import com.alexcfa.yourweather.ui.screens.current.CurrentScreen

@Composable
fun Navigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "currentScreen") {
        composable("currentScreen") {
            CurrentScreen(onHourlyClick = { navController.navigate("hourly") })
        }

        composable("hourly") {
            HourlyScreen(
                onBack = { navController.popBackStack() }
            )
        }

    }

}