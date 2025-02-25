package com.bartosboth.weatherforecast.ui.screens.main

import androidx.lifecycle.ViewModel
import com.bartosboth.weatherforecast.data.DataOrException
import com.bartosboth.weatherforecast.data.model.Weather
import com.bartosboth.weatherforecast.data.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: WeatherRepository
) : ViewModel() {

    suspend fun getWeatherData(city: String): DataOrException<Weather, Boolean, Exception> {
        return repository.getWeather(city)
    }
}