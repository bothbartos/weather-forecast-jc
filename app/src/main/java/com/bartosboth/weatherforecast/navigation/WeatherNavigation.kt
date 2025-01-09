package com.bartosboth.weatherforecast.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.bartosboth.weatherforecast.screens.main.MainScreen
import com.bartosboth.weatherforecast.screens.main.MainViewModel
import com.bartosboth.weatherforecast.screens.search.SearchScreen
import com.bartosboth.weatherforecast.screens.splash.WeatherSplashScreen

@Composable
fun WeatherNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = SplashScreen) {
        composable<SplashScreen> {
            WeatherSplashScreen(navController = navController)
        }

        composable<MainScreen> { backStackEntry ->
            val mainScreen: MainScreen = backStackEntry.toRoute()
            val mainViewModel: MainViewModel = hiltViewModel()

            MainScreen(navController =  navController, viewModel = mainViewModel, city = mainScreen.city)
        }

        composable<SearchScreen> {
            SearchScreen(navController = navController,
                onCitySelected = { city ->
                    navController.navigate(MainScreen(city = city))
                }
            )
        }
    }
}