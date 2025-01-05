package com.bartosboth.weatherforecast.model

data class Weather(
    val current: Current,
    val forecast: Forecast,
    val location: Location
)