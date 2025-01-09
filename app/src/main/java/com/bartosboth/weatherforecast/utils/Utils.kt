package com.bartosboth.weatherforecast.utils

import android.annotation.SuppressLint
import android.graphics.Color
import androidx.compose.foundation.isSystemInDarkTheme
import java.text.SimpleDateFormat

@SuppressLint("SimpleDateFormat")
fun formatDate(date: String): String {
    val inputFormat = SimpleDateFormat("yyyy-MM-dd")

    val outputFormat = SimpleDateFormat("EEE, MMM d")

    val dateObj = inputFormat.parse(date)
    return outputFormat.format(dateObj!!)
}

@SuppressLint("SimpleDateFormat")
fun formatDateToDay(date: String): String {
    val inputFormat = SimpleDateFormat("yyyy-MM-dd")
    val outputFormat = SimpleDateFormat("EEE")
    val dateObj = inputFormat.parse(date)
    return outputFormat.format(dateObj!!)
}