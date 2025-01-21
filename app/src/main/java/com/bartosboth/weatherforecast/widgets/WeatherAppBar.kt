package com.bartosboth.weatherforecast.widgets

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.bartosboth.weatherforecast.model.Favourite
import com.bartosboth.weatherforecast.navigation.AboutScreen
import com.bartosboth.weatherforecast.navigation.FavouritesScreen
import com.bartosboth.weatherforecast.navigation.SettingsScreen
import com.bartosboth.weatherforecast.screens.favourites.FavouriteViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherAppBar(
    title: String = "Title",
    icon: ImageVector? = null,
    isMainScreen: Boolean = true,
    navController: NavController,
    viewModel: FavouriteViewModel = hiltViewModel(),
    onAddActionClicked: () -> Unit = {},
    onButtonClicked: () -> Unit = {}
) {
    val showDialog = remember {
        mutableStateOf(false)
    }

    val showToast = remember {
        mutableStateOf(false)
    }

    val isAdded = remember {
        mutableStateOf(false)
    }



    if (showDialog.value) {
        ShowSettingDropdown(showDialog = showDialog, navController = navController)
    }


    CenterAlignedTopAppBar(
        title = {
            if (isMainScreen) {
                Text(
                    text = title,
                    color = MaterialTheme.colorScheme.onSurface,
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = MaterialTheme.typography.titleLarge.fontSize
                    )
                )
            } else {
                Text(
                    text = title,
                    color = MaterialTheme.colorScheme.onSurface,
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = MaterialTheme.typography.titleLarge.fontSize
                    )
                )
            }
        },
        actions = {
            if (isMainScreen) {

                IconButton(onClick = {
                    onAddActionClicked.invoke()
                }) {
                    Icon(imageVector = Icons.Default.Search, contentDescription = "search icon")
                }
                IconButton(onClick = {
                    showDialog.value = true
                }) {
                    Icon(imageVector = Icons.Default.MoreVert, contentDescription = "more icon")
                }

            }

        },
        navigationIcon = {
           if(icon != null) {
                IconButton(onClick = onButtonClicked) {
                    Icon(imageVector = icon,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.clickable {
                            onButtonClicked.invoke()
                        })
                }
            }
            if(isMainScreen){
                val isFavourite = viewModel.favouriteList.collectAsState().value.filter {item ->
                    (item.city == title.split(",")[0])
                }
                if(isFavourite.isEmpty()){
                    Icon(imageVector = Icons.Default.FavoriteBorder,
                        contentDescription = "favourite",
                        modifier = Modifier.scale(0.9f)
                            .clickable {
                                viewModel.insertFavourite(
                                    Favourite(
                                        city = title.split(",")[0],
                                        country = title.split(",")[1]
                                    )
                                ).run {
                                    showToast.value = true
                                    isAdded.value = true
                                }
                            })
                }else{
                    showToast.value = false
                    Icon(imageVector = Icons.Default.Favorite,
                        contentDescription = "favourite",
                        modifier = Modifier.scale(0.9f)
                            .clickable {
                                viewModel.deleteFavourite(
                                    Favourite(
                                        city = title.split(",")[0],
                                        country = title.split(",")[1]
                                    )
                                ).run {
                                    showToast.value = true
                                    isAdded.value = false
                                }
                            }
                    )
                }
                ShowToast(context = navController.context, showToast = showToast, isAdded = isAdded)
           }
        },
        colors = TopAppBarDefaults.topAppBarColors(MaterialTheme.colorScheme.surfaceContainer)

    )

}

@Composable
fun ShowToast(context: Context, showToast: MutableState<Boolean>, isAdded: MutableState<Boolean>) {
    if(showToast.value && isAdded.value){
        Toast.makeText(context,"Added to favourites", Toast.LENGTH_SHORT).show()
        showToast.value = false
    }else if(showToast.value && !isAdded.value){
        Toast.makeText(context,"Removed from favourites", Toast.LENGTH_SHORT).show()
        showToast.value = false
    }
}


@Composable
fun ShowSettingDropdown(
    showDialog: MutableState<Boolean>,
    navController: NavController,
) {
    val menuItems = listOf("Favourites", "About", "Settings")
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentSize(Alignment.TopEnd)
            .absolutePadding(top = 45.dp, right = 20.dp)
    ) {
        DropdownMenu(
            modifier = Modifier
                .width(150.dp)
                .background(MaterialTheme.colorScheme.surfaceContainer),
            expanded = showDialog.value,
            onDismissRequest = {
                showDialog.value = false
            }
        ) {
            menuItems.forEachIndexed { index, title ->
                DropdownMenuItem(
                    modifier = Modifier.background(MaterialTheme.colorScheme.surfaceContainer),
                    leadingIcon = {
                        when (index) {
                            0 -> {
                                Icon(
                                    imageVector = Icons.Default.FavoriteBorder,
                                    contentDescription = title
                                )
                            }

                            1 -> {
                                Icon(
                                    imageVector = Icons.Default.Info,
                                    contentDescription = title
                                )
                            }

                            2 -> {
                                Icon(
                                    imageVector = Icons.Default.Settings,
                                    contentDescription = title
                                )
                            }
                        }
                    },
                    text = { Text(text = title) },
                    onClick = {
                        showDialog.value = false
                        when (index) {
                            0 -> navController.navigate(FavouritesScreen)
                            1 -> navController.navigate(AboutScreen)
                            2 -> navController.navigate(SettingsScreen)
                        }
                    }
                )
            }
        }
    }
}

