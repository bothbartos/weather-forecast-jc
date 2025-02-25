package com.bartosboth.weatherforecast.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.bartosboth.weatherforecast.ui.screens.about.AboutScreen
import com.bartosboth.weatherforecast.ui.screens.detail.DetailScreen
import com.bartosboth.weatherforecast.ui.screens.favourites.FavouritesScreen
import com.bartosboth.weatherforecast.ui.screens.main.MainScreen
import com.bartosboth.weatherforecast.ui.screens.main.MainViewModel
import com.bartosboth.weatherforecast.ui.screens.search.SearchScreen
import com.bartosboth.weatherforecast.ui.screens.settings.SettingsScreen
import com.bartosboth.weatherforecast.ui.screens.settings.SettingsViewModel
import com.bartosboth.weatherforecast.ui.screens.splash.WeatherSplashScreen

@Composable
fun WeatherNavigation() {
    val navController = rememberNavController()
    val mainViewModel: MainViewModel = hiltViewModel()

    NavHost(navController = navController, startDestination = SplashScreen) {
        composable<SplashScreen> {
            WeatherSplashScreen(navController = navController)
        }

        composable<MainScreen> { backStackEntry ->
            val mainScreen: MainScreen = backStackEntry.toRoute()
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
        composable<DetailScreen> { backStackEntry ->
            val detailScreen: DetailScreen = backStackEntry.toRoute()
            val isImperial = hiltViewModel<SettingsViewModel>().unitSetting.collectAsState().value

            val forecastDays by mainViewModel.forecastDays.collectAsState()
            val forecastDay = forecastDays.getOrNull(detailScreen.index)

            DetailScreen(
                navController = navController,
                forecastDay = forecastDay,
                isImperial = isImperial
            )
        }

    }
}
