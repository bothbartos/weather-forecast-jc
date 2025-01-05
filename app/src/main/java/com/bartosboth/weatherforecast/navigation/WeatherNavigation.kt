package com.bartosboth.weatherforecast.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.bartosboth.weatherforecast.navigation.SplashScreen
import com.bartosboth.weatherforecast.screens.WeatherSplashScreen
import java.lang.reflect.Modifier

@Composable
fun WeatherNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = SplashScreen) {
        composable<SplashScreen>{
            WeatherSplashScreen(navController = navController)
        }
    }
}