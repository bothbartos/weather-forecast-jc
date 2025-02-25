package com.bartosboth.weatherforecast.ui.screens.detail

import android.util.Log
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import com.bartosboth.weatherforecast.data.model.Forecastday
import com.bartosboth.weatherforecast.ui.screens.main.MainViewModel

@Composable
fun DetailScreen(
    navController: NavController,
    index: Int,
    forecastDay: Forecastday?,
    isImperial: Boolean
) {
    if(forecastDay != null) {

    }else{
        Text("No data found")
    }

}