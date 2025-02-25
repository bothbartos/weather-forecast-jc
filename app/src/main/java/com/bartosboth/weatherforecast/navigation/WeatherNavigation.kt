package com.bartosboth.weatherforecast.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.bartosboth.weatherforecast.ui.screens.about.AboutScreen
import com.bartosboth.weatherforecast.ui.screens.favourites.FavouritesScreen
import com.bartosboth.weatherforecast.ui.screens.main.MainScreen
import com.bartosboth.weatherforecast.ui.screens.main.MainViewModel
import com.bartosboth.weatherforecast.ui.screens.search.SearchScreen
import com.bartosboth.weatherforecast.ui.screens.settings.SettingsScreen
import com.bartosboth.weatherforecast.ui.screens.splash.WeatherSplashScreen

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

            MainScreen(navController =  navController, mainViewModel = mainViewModel, city = mainScreen.city)
        }

        composable<SearchScreen> {
            SearchScreen(navController = navController,
                onCitySelected = { city ->
                    navController.navigate(MainScreen(city = city))
                }
            )
        }
        composable<FavouritesScreen>{
            FavouritesScreen(navController = navController)
        }
        composable<AboutScreen>{
            AboutScreen(navController = navController)
        }
        composable<SettingsScreen>{
            SettingsScreen(navController = navController)
        }
    }
}