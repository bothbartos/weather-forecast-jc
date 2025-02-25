package com.bartosboth.weatherforecast.ui.screens.splash

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.bartosboth.weatherforecast.R
import com.bartosboth.weatherforecast.navigation.MainScreen
import kotlinx.coroutines.delay
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController

@Composable
fun WeatherSplashScreen(navController: NavController, modifier: Modifier = Modifier) {
    val scale = remember {
        Animatable(0f)

    }
    val imageColor = if(isSystemInDarkTheme()) Color.White else Color.Black

    LaunchedEffect(key1 = true, block =  {
        scale.animateTo(targetValue = 0.9f,
            animationSpec = tween(durationMillis = 800,
                easing = {
                    OvershootInterpolator(8f)
                        .getInterpolation(it)
                }))
        delay(2000L)
        navController.navigate(MainScreen())
    })

    Box(contentAlignment = Alignment.Center,
        modifier = modifier.fillMaxSize()
            .background(MaterialTheme.colorScheme.background),

        ){
        Surface(
            modifier = modifier
                .size(350.dp)
                .fillMaxSize()
                .scale(scale.value),
            shape = CircleShape,
            color = MaterialTheme.colorScheme.surfaceContainer,
            border = BorderStroke(width = 2.dp, color = Color.LightGray),
        ) {
            Column(
                modifier = modifier.padding(1.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    modifier = Modifier.size(95.dp),
                    painter = painterResource(id = R.drawable.sun),
                    contentDescription = "Sun icon",
                    contentScale = ContentScale.Fit,
                    colorFilter = ColorFilter.tint(imageColor)
                )
                Text(
                    text = "Loading...",
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        }
    }
}


