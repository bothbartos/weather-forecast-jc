package com.bartosboth.weatherforecast.data.model

data class Forecastday(
    val astro: Astro,
    val date: String,
    val day: Day,
    val hour: List<Hour>
)