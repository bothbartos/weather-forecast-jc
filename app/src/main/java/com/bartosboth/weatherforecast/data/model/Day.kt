package com.bartosboth.weatherforecast.data.model

data class Day(
    val avghumidity: Int, //kell
    val condition: Condition,
    val maxtemp_c: Double,
    val maxtemp_f: Double,
    val maxwind_kph: Double,
    val maxwind_mph: Double,
    val mintemp_c: Double,
    val mintemp_f: Double,
)