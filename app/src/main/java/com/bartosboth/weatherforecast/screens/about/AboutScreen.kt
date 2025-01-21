package com.bartosboth.weatherforecast.screens.about

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController
import com.bartosboth.weatherforecast.R
import com.bartosboth.weatherforecast.widgets.WeatherAppBar

@Composable
fun AboutScreen(navController: NavController) {
    Scaffold(
        topBar = {
            WeatherAppBar(
                navController = navController,
                title = "About",
                icon = Icons.AutoMirrored.Filled.ArrowBack,
                isMainScreen = false
            ) {
                navController.popBackStack()
            }
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(R.string.about_app),
                style = MaterialTheme.typography.titleMedium,
                fontStyle = MaterialTheme.typography.bodyMedium.fontStyle,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = stringResource(R.string.api_used),
                style = MaterialTheme.typography.titleMedium,
                fontStyle = MaterialTheme.typography.bodyMedium.fontStyle,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontWeight = FontWeight.Bold
            )
        }

    }
}




