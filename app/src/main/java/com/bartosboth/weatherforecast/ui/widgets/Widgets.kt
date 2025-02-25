package com.bartosboth.weatherforecast.ui.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.bartosboth.weatherforecast.R
import com.bartosboth.weatherforecast.data.model.Forecastday
import com.bartosboth.weatherforecast.data.model.Weather
import com.bartosboth.weatherforecast.navigation.DetailScreen
import com.bartosboth.weatherforecast.utils.formatDateToDay
import kotlin.math.roundToInt

@Composable
fun SunriseSunsetRow(weather: Weather) {
    val imageColor =
        if (isSystemInDarkTheme()) MaterialTheme.colorScheme.onBackground else MaterialTheme.colorScheme.onSurface
    Row(
        modifier = Modifier
            .padding(12.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row() {
            Image(
                modifier = Modifier.size(20.dp),
                painter = painterResource(id = R.drawable.sunrise),
                contentDescription = "sunrise",
                colorFilter = ColorFilter.tint(imageColor)
            )
            Text(
                text = weather.forecast.forecastday[0].astro.sunrise,
                style = MaterialTheme.typography.titleMedium
            )
        }
        Row() {
            Text(
                text = weather.forecast.forecastday[0].astro.sunset,
                style = MaterialTheme.typography.titleMedium
            )
            Image(
                modifier = Modifier.size(20.dp),
                painter = painterResource(R.drawable.sunset),
                contentDescription = "sunset",
                colorFilter = ColorFilter.tint(imageColor)
            )
        }
    }
}

@Composable
fun WeatherStateImage(modifier: Modifier = Modifier, imageUrl: String, imageSize: Int = 100) {
    AsyncImage(
        model = imageUrl,
        contentDescription = "icon",
        modifier = modifier.size(imageSize.dp)
    )
}

@Composable
fun WindPressureRow(weather: Weather, isImperial: Boolean) {
    val imageColor =
        if (isSystemInDarkTheme()) MaterialTheme.colorScheme.onBackground else MaterialTheme.colorScheme.onSurface
    Row(
        modifier = Modifier
            .padding(12.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Image(
            modifier = Modifier.size(30.dp),
            painter = painterResource(id = R.drawable.humidity),
            contentDescription = "humidity",
            colorFilter = ColorFilter.tint(imageColor)
        )
        Text(
            text = "${weather.current.humidity}%",
            style = MaterialTheme.typography.titleMedium
        )
        Image(
            modifier = Modifier.size(30.dp),
            painter = painterResource(id = R.drawable.wind),
            contentDescription = "Invert Black to White",
            colorFilter = ColorFilter.tint(imageColor)
        )
        Text(
            text = if(isImperial)"${weather.current.wind_mph} MPH" else "${weather.current.wind_kph} km/h",
            style = MaterialTheme.typography.titleMedium
        )
        Image(
            modifier = Modifier.size(30.dp),
            painter = painterResource(id = R.drawable.pressure),
            contentDescription = "pressure",
            colorFilter = ColorFilter.tint(imageColor)
        )
        Text(
            text = "${weather.current.pressure_mb} mb",
            style = MaterialTheme.typography.titleMedium
        )
    }
}


@Composable
fun ForecastLazyColumn(weather: Weather, isImperial: Boolean, navController: NavController) {
    Text(
        text = "3 day forecast",
        style = MaterialTheme.typography.titleMedium,
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        textAlign = TextAlign.Center
    )
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(3.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp, vertical = 5.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                modifier = Modifier.weight(1f),
                text = "Day",
                style = MaterialTheme.typography.labelSmall,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                modifier = Modifier.weight(1f),
                text = "Condition",
                style = MaterialTheme.typography.labelSmall,
                textAlign = TextAlign.Center
            )
            Text(
                modifier = Modifier.weight(1f),
                text = "Max",
                style = MaterialTheme.typography.labelSmall,
                textAlign = TextAlign.Center
            )
            Text(
                modifier = Modifier.weight(1f),
                text = "Min",
                style = MaterialTheme.typography.labelSmall,
                textAlign = TextAlign.Center
            )
        }
        Surface(
            modifier = Modifier.fillMaxSize(),
            shape = RoundedCornerShape(10.dp)
        ) {
            LazyColumn {
                items(weather.forecast.forecastday.size) { index ->
                    ForecastRow(weather.forecast.forecastday[index],
                        isImperial = isImperial,
                        onItemClicked = { navController.navigate(DetailScreen(index))})
                }
            }
        }
    }
}

@Composable
fun ForecastRow(weather: Forecastday, isImperial: Boolean, onItemClicked: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(3.dp)
            .clickable { onItemClicked() }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp, vertical = 5.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                modifier = Modifier.weight(1f),
                text = formatDateToDay(weather.date),
                textAlign = TextAlign.Center
            )
            WeatherStateImage(
                modifier = Modifier.weight(1f),
                imageUrl = "https:${weather.day.condition.icon}",
                imageSize = 50
            )
            Card(
                modifier = Modifier
                    .padding(4.dp)
                    .weight(1f),
                shape = RoundedCornerShape(10.dp),

                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),

                ) {
                Text(
                    modifier = Modifier.fillMaxWidth().padding(8.dp),
                    text = weather.day.condition.text,
                    style = MaterialTheme.typography.labelSmall,
                    textAlign = TextAlign.Center
                )
            }
            Text(
                modifier = Modifier.weight(1f),
                text = if (isImperial)"${weather.day.maxtemp_f.roundToInt()}°F" else "${weather.day.maxtemp_c.roundToInt()}°C" ,
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center
            )
            Text(
                modifier = Modifier.weight(1f),
                text = if(isImperial)"${weather.day.mintemp_c.roundToInt()}°F" else "${weather.day.mintemp_f.roundToInt()}°C",
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center
            )
        }
    }
}