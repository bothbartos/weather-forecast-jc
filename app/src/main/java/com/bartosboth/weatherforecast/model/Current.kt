package com.bartosboth.weatherforecast.model

data class Current(
    val condition: Condition,
    val humidity: Int,
    val pressure_in: Double,
    val pressure_mb: Int,
    val temp_c: Double,
    val temp_f: Double,
    val wind_kph: Double,
    val wind_mph: Double,
)