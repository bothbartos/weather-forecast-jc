package com.bartosboth.weatherforecast.ui.screens.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.bartosboth.weatherforecast.data.model.Weather
import com.bartosboth.weatherforecast.navigation.SearchScreen
import com.bartosboth.weatherforecast.ui.screens.settings.SettingsViewModel
import com.bartosboth.weatherforecast.ui.widgets.ForecastLazyColumn
import com.bartosboth.weatherforecast.ui.widgets.SunriseSunsetRow
import com.bartosboth.weatherforecast.ui.widgets.WeatherAppBar
import com.bartosboth.weatherforecast.ui.widgets.WeatherStateImage
import com.bartosboth.weatherforecast.ui.widgets.WindPressureRow
import com.bartosboth.weatherforecast.utils.formatDate
import kotlin.math.roundToInt


@Composable
fun MainScreen(
    navController: NavController,
    mainViewModel: MainViewModel,
    city: String = "Budapest"
) {
    val weatherData by mainViewModel.weatherData.collectAsState()

    LaunchedEffect(city) {
        mainViewModel.getWeatherData(city)
    }

    when {
        weatherData.loading == true -> {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator()
            }
        }
        weatherData.data != null -> {
            MainScaffold(
                weather = weatherData.data!!,
                navController = navController
            )
        }
        weatherData.e != null -> {
            Text("Error: ${weatherData.e?.message}")
        }
    }
}


@Composable
fun MainScaffold(weather: Weather, navController: NavController) {
    Scaffold(topBar = {
        WeatherAppBar(
            title = weather.location.name + ", ${weather.location.country}",
            navController = navController,
            onAddActionClicked = {
                navController.navigate(SearchScreen)
            }
        ) {
            navController.popBackStack()
        }
    }) { innerPadding ->
        MainContent(data = weather, modifier = Modifier.padding(innerPadding), navController = navController)
    }
}

@Composable
fun MainContent(data: Weather, modifier: Modifier, navController: NavController) {
    val isImperial: Boolean = hiltViewModel<SettingsViewModel>().unitSetting.collectAsState().value

    Column(
        modifier = modifier
            .padding(4.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = formatDate(data.location.localtime.split(" ")[0]),
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(6.dp)
        )
        Surface(
            modifier = Modifier
                .padding(4.dp)
                .size(250.dp),
            shape = CircleShape,
            color = MaterialTheme.colorScheme.primary
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(2.dp)
            ) {
                WeatherStateImage(imageUrl = "https:${data.current.condition.icon}")
                Text(
                    text = if(isImperial) "${data.current.temp_f.roundToInt()}°F" else "${data.current.temp_c.roundToInt()}°C",
                    style = MaterialTheme.typography.displayMedium,
                    fontWeight = MaterialTheme.typography.displayMedium.fontWeight,
                    fontStyle = FontStyle.Italic
                )
                Text(
                    text = data.current.condition.text,
                    style = MaterialTheme.typography.titleMedium,
                    fontStyle = FontStyle.Italic
                )
            }
        }
        WindPressureRow(weather = data, isImperial = isImperial)
        HorizontalDivider(thickness = 2.dp, color = Color.Gray)
        SunriseSunsetRow(weather = data)
        HorizontalDivider(thickness = 2.dp, color = Color.Gray)
        ForecastLazyColumn(weather = data, isImperial = isImperial, navController = navController)
    }
}
