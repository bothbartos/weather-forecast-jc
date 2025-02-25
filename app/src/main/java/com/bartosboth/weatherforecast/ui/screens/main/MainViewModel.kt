package com.bartosboth.weatherforecast.ui.screens.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bartosboth.weatherforecast.data.DataOrException
import com.bartosboth.weatherforecast.data.model.Forecastday
import com.bartosboth.weatherforecast.data.model.Weather
import com.bartosboth.weatherforecast.data.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: WeatherRepository
) : ViewModel() {

    private val _weatherData = MutableStateFlow<DataOrException<Weather, Boolean, Exception>>(DataOrException(loading = true))
    val weatherData: StateFlow<DataOrException<Weather, Boolean, Exception>> = _weatherData.asStateFlow()

    private val _forecastDays = MutableStateFlow<List<Forecastday>>(emptyList())
    val forecastDays: StateFlow<List<Forecastday>> = _forecastDays.asStateFlow()

    fun getWeatherData(city: String) {
        viewModelScope.launch {
            _weatherData.value = DataOrException(loading = true)
            try {
                _weatherData.value = repository.getWeather(city)
            } catch (e: Exception) {
                _weatherData.value = DataOrException(e = e, loading = false)
            }
            _weatherData.value.data?.let { weather ->
                _forecastDays.value = weather.forecast.forecastday
            }
        }
    }

}