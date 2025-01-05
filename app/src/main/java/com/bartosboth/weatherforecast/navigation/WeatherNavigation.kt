package com.bartosboth.weatherforecast.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.bartosboth.weatherforecast.screens.main.MainScreen
import com.bartosboth.weatherforecast.screens.splash.WeatherSplashScreen

@Composable
fun WeatherNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = SplashScreen) {
        composable<SplashScreen>{
            WeatherSplashScreen(navController = navController)
        }
        composable<MainScreen>{
            MainScreen(navController = navController)
        }
    }
}