package com.bartosboth.weatherforecast

import com.bartosboth.weatherforecast.data.DataOrException
import com.bartosboth.weatherforecast.data.model.Astro
import com.bartosboth.weatherforecast.data.model.Condition
import com.bartosboth.weatherforecast.data.model.Current
import com.bartosboth.weatherforecast.data.model.Day
import com.bartosboth.weatherforecast.data.model.Forecast
import com.bartosboth.weatherforecast.data.model.Forecastday
import com.bartosboth.weatherforecast.data.model.Hour
import com.bartosboth.weatherforecast.data.model.Location
import com.bartosboth.weatherforecast.data.model.Weather
import com.bartosboth.weatherforecast.data.network.WeatherApi
import com.bartosboth.weatherforecast.data.repository.WeatherRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.doThrow
import org.mockito.kotlin.whenever

class WeatherRepositoryTest {

    @Mock
    private lateinit var weatherApi: WeatherApi

    private lateinit var repository: WeatherRepository

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        repository = WeatherRepository(weatherApi)
    }

    @Test
    fun `getWeather returns weather data on success`() = runTest {
        // Arrange
        val city = "London"
        val mockForecastDays = listOf(
            Forecastday(
                astro = Astro(sunset = "16:54", sunrise = "06:34"),
                date = "2025.02.25",
                day = Day(
                    avghumidity = 1,
                    condition = Condition(icon = "", text = "Sunny"),
                    maxtemp_c = 1.0,
                    maxtemp_f = 1.0,
                    maxwind_kph = 1.0,
                    maxwind_mph = 1.0,
                    mintemp_c = 1.0,
                    mintemp_f = 1.0
                ),
                hour = listOf(
                    Hour(
                        condition = Condition(icon = "", text = ""),
                        humidity = 1,
                        temp_c = 1.0,
                        temp_f = 1.0,
                        time = "",
                        pressure_mb = 1
                    )
                )
            )
        )
        val mockWeather = Weather(
            current = Current(
                condition = Condition(icon = "", text = ""),
                temp_c = 1.0,
                temp_f = 1.0,
                wind_kph = 1.0,
                wind_mph = 1.0,
                humidity = 1,
                pressure_mb = 1,
                pressure_in = 1.0
            ),
            forecast = Forecast(forecastday = mockForecastDays),
            location = Location(country = "UK", name = "London", localtime = "12:00")
        )
        whenever(weatherApi.getWeather(query = city)).thenReturn(mockWeather)

        // Act
        val result = repository.getWeather(city)

        // Assert
        assertEquals(DataOrException(data = mockWeather, loading = false, e = null), result)
    }

    @Test
    fun `getWeather returns exception on failure`() = runTest {
        // Arrange
        val city = "NonexistentCity"
        val exception = RuntimeException("City not found")
        doThrow(exception).`when`(weatherApi).getWeather(query = city)

        // Act
        val result = repository.getWeather(city)

        // Assert
        assertEquals(DataOrException(data = null, loading = false, e = exception), result)
    }
}


