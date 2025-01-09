package com.bartosboth.weatherforecast.screens.main

import androidx.lifecycle.ViewModel
import com.bartosboth.weatherforecast.data.DataOrException
import com.bartosboth.weatherforecast.model.Weather
import com.bartosboth.weatherforecast.repository.WeatherRepository
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