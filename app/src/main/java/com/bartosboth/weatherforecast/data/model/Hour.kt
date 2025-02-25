package com.bartosboth.weatherforecast.data.model

data class Hour(
    val condition: Condition,
    val humidity: Int,
    val temp_c: Double,
    val temp_f: Double,
    val time: String,
    val pressure_mb: Int
)