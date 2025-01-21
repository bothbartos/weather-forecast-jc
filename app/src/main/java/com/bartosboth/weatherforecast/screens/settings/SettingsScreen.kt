package com.bartosboth.weatherforecast.screens.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.FilledTonalIconToggleButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.bartosboth.weatherforecast.widgets.WeatherAppBar

@Composable
fun SettingsScreen(
    navController: NavController,
    settingsViewModel: SettingsViewModel = hiltViewModel()
) {

    var unitToggleChange = settingsViewModel.unitSetting.collectAsState().value

    Scaffold(topBar = {
        WeatherAppBar(
            title = "Settings",
            icon = Icons.AutoMirrored.Filled.ArrowBack,
            isMainScreen = false,
            navController = navController
        ){
            navController.popBackStack()
        }
    }) { innerPadding ->
        Surface(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Change Unit of measurement",
                    modifier = Modifier.padding(15.dp)
                )

                FilledTonalIconToggleButton(modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .clip(RoundedCornerShape(15.dp))
                    .padding(5.dp),
                    checked = true,
                    onCheckedChange = {
                        unitToggleChange = !unitToggleChange
                        settingsViewModel.toggleUnitSetting()
                    }) {
                    if (unitToggleChange) Text("Metric") else Text("Imperial")
                }


            }
        }
    }

}

@Preview
@Composable
fun SettingsScreenPreview() {
    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Change Unit of measurement",
                modifier = Modifier.padding(15.dp)
            )
            FilledTonalIconToggleButton(
                checked = true, onCheckedChange = {
                    //TODO
                },
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .clip(RoundedCornerShape(15.dp))
                    .padding(5.dp)
            ) {
                Text("Metric")

            }


        }
    }
}