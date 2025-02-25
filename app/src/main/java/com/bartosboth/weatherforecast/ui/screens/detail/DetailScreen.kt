package com.bartosboth.weatherforecast.ui.screens.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.bartosboth.weatherforecast.data.model.Forecastday
import com.bartosboth.weatherforecast.data.model.Hour
import com.bartosboth.weatherforecast.ui.widgets.SunriseSunsetRow
import com.bartosboth.weatherforecast.ui.widgets.WeatherAppBar
import com.bartosboth.weatherforecast.ui.widgets.WeatherInfoDisplay
import com.bartosboth.weatherforecast.ui.widgets.WeatherStateImage
import com.bartosboth.weatherforecast.ui.widgets.WindPressureRow
import kotlin.math.roundToInt

@Composable
fun DetailScreen(
    navController: NavController,
    forecastDay: Forecastday?,
    isImperial: Boolean
) {

    forecastDay?.let { nonNullForecastDay ->
        Scaffold(topBar = {
            WeatherAppBar(
                title = nonNullForecastDay.date,
                icon = Icons.AutoMirrored.Filled.ArrowBack,
                isMainScreen = false,
                navController = navController
            ) {
                navController.popBackStack()
            }
        }) { innerPadding ->
            DetailContent(
                modifier = Modifier.padding(innerPadding),
                forecastDay = nonNullForecastDay,
                isImperial = isImperial
            )
        }
    } ?: run {
        Text("No data found")
    }
}

@Composable
fun DetailContent(modifier: Modifier = Modifier, forecastDay: Forecastday, isImperial: Boolean) {
    Column(
        modifier = modifier
            .padding(4.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Surface(
            modifier = Modifier
                .padding(4.dp)
                .size(250.dp),
            shape = CircleShape,
            color = MaterialTheme.colorScheme.primary
        ) {
            WeatherInfoDisplay(
                imageUrl = "https:${forecastDay.day.condition.icon}",
                temperature = if (isImperial) {
                    "${forecastDay.day.mintemp_f.roundToInt()}°F / ${forecastDay.day.maxtemp_f.roundToInt()}°F"
                } else {
                    "${forecastDay.day.mintemp_c.roundToInt()}°C / ${forecastDay.day.maxtemp_c.roundToInt()}°C"
                },
                condition = forecastDay.day.condition.text
            )
        }
        WindPressureRow(
            humidity = forecastDay.day.avghumidity,
            windSpeed = if (isImperial) forecastDay.day.maxwind_mph else forecastDay.day.maxwind_kph,
            pressure = forecastDay.hour[12].pressure_mb,
            isImperial = isImperial
        )
        HorizontalDivider(thickness = 2.dp, color = Color.Gray)
        SunriseSunsetRow(
            sunrise = forecastDay.astro.sunrise,
            sunset = forecastDay.astro.sunset
        )
        HorizontalDivider(thickness = 2.dp, color = Color.Gray)
        HourlyLazyRow(data = forecastDay.hour, isImperial = isImperial)
    }
}

@Composable
fun HourlyCard(hour: Hour, isImperial: Boolean) {
    Card(shape = CircleShape,
        modifier = Modifier.padding(3.dp),
        colors = CardColors(containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
            disabledContainerColor = MaterialTheme.colorScheme.primaryContainer,
            disabledContentColor = MaterialTheme.colorScheme.onPrimaryContainer
            )){
        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween) {
            Text(
                text = if (isImperial) {
                    hour.temp_f.roundToInt().toString() + "°F"
                } else {
                    hour.temp_c.roundToInt().toString() + "°C"
                },
                style = TextStyle(
                    fontStyle = MaterialTheme.typography.bodySmall.fontStyle,
                    fontWeight = MaterialTheme.typography.bodySmall.fontWeight
                )
            )
            WeatherStateImage(
                imageUrl = "https:${hour.condition.icon}",
                imageSize = 50
            )
            Text(
                text = hour.time.split(" ")[1],
                style = TextStyle(
                    fontStyle = MaterialTheme.typography.bodySmall.fontStyle,
                    fontWeight = MaterialTheme.typography.bodySmall.fontWeight
                )
            )

        }
    }

}

@Composable
fun HourlyLazyRow(data: List<Hour>, isImperial: Boolean) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(14.dp),
        shape = RoundedCornerShape(27.dp),
        colors = CardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
            contentColor = MaterialTheme.colorScheme.onSurfaceVariant,
            disabledContainerColor = MaterialTheme.colorScheme.surfaceVariant,
            disabledContentColor = MaterialTheme.colorScheme.onSurfaceVariant
        )
    ) {
        Text(
            text = "Hourly forecast",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            textAlign = TextAlign.Left
        )
        LazyRow(modifier = Modifier.padding(7.dp)) {
            items(data.size) { index ->
                HourlyCard(data[index], isImperial = isImperial)
            }
        }
    }
}

