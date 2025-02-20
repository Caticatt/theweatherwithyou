package com.alexcfa.yourweather.ui.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.alexcfa.yourweather.ui.screens.hourly.HourlyScreen
import com.alexcfa.yourweather.ui.screens.current.CurrentScreen

@RequiresApi(Build.VERSION_CODES.O)
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