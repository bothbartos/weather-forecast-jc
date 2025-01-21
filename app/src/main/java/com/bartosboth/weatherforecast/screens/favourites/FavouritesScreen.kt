package com.bartosboth.weatherforecast.screens.favourites

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.bartosboth.weatherforecast.model.Favourite
import com.bartosboth.weatherforecast.navigation.MainScreen
import com.bartosboth.weatherforecast.widgets.WeatherAppBar

@Composable
fun FavouritesScreen(
    navController: NavController,
    viewModel: FavouriteViewModel = hiltViewModel()
) {
    val list = viewModel.favouriteList.collectAsState().value

    Scaffold(topBar = {
        WeatherAppBar(
            navController = navController,
            title = "Favourite Cities",
            icon = Icons.AutoMirrored.Filled.ArrowBack,
            isMainScreen = false,
            viewModel = viewModel
        ) {
            navController.popBackStack()
        }
    }) { innerPadding ->
        Surface(modifier = Modifier
            .fillMaxWidth()
            .padding(innerPadding)) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                LazyColumn {
                    items(items = list) { it ->
                        FavouriteCityRow(
                            favouriteCity = it,
                            navController = navController
                            )
                        {
                            viewModel.deleteFavourite(it)
                        }
                    }

                }
            }
        }
    }
}


@Composable
fun FavouriteCityRow(
    favouriteCity: Favourite = Favourite("Budapest", "Hungary"),
    navController: NavController,
    onDelete:(Favourite) -> Unit = {}
) {
    Surface(
        modifier = Modifier
            .padding(3.dp)
            .fillMaxWidth()
            .height(50.dp)
            .clickable {
                val city = favouriteCity.city
                navController.navigate(MainScreen(city))
            },
        shape = RoundedCornerShape(15.dp),
        color = MaterialTheme.colorScheme.primaryContainer

    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(5.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Card(modifier = Modifier.padding(5.dp).weight(1f),
                colors = CardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    disabledContainerColor = MaterialTheme.colorScheme.primaryContainer,
                    disabledContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                )
            ){
                Text(modifier = Modifier.weight(1f), text = favouriteCity.city)

            }
            Text(modifier = Modifier.weight(1f), text = favouriteCity.country)
            IconButton(
                onClick = {
                    onDelete(favouriteCity)
                },
                modifier = Modifier.weight(1f)
            ) {
                Icon(imageVector = Icons.Default.Delete, contentDescription = "delete")
            }
        }
    }

}

@Preview
@Composable
fun FavouriteRow(){
    Surface(
        modifier = Modifier
            .padding(3.dp)
            .fillMaxWidth()
            .height(50.dp)
            .clickable {
            },
        shape = RoundedCornerShape(15.dp),
        color = MaterialTheme.colorScheme.primaryContainer

    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 15.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(modifier = Modifier.weight(1f),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically){
                Text(modifier = Modifier.weight(1f), text = "Budapest")

                Text(modifier = Modifier.weight(1f), text = "Hungary")
                IconButton(
                    onClick = {
                    },
                    modifier = Modifier.weight(1f)
                        .size(30.dp)
                ) {
                    Icon(imageVector = Icons.Default.Delete, contentDescription = "delete")
                }
            }
        }
    }
}