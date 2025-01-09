package com.bartosboth.weatherforecast.navigation

import kotlinx.serialization.Serializable

@Serializable
data class MainScreen(val city: String = "Budapest")