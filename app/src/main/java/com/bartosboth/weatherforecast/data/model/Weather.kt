package com.bartosboth.weatherforecast.data.model

data class Weather(
    val current: Current,
    val forecast: Forecast,
    val location: Location
)