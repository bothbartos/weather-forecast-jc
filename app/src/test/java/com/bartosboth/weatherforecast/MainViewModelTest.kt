package com.bartosboth.weatherforecast

import com.bartosboth.weatherforecast.data.model.Forecast
import com.bartosboth.weatherforecast.data.model.Forecastday
import com.bartosboth.weatherforecast.data.model.Weather
import com.bartosboth.weatherforecast.data.repository.WeatherRepository
import com.bartosboth.weatherforecast.ui.screens.main.MainViewModel
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import com.bartosboth.weatherforecast.data.DataOrException
import com.bartosboth.weatherforecast.data.model.Astro
import com.bartosboth.weatherforecast.data.model.Condition
import com.bartosboth.weatherforecast.data.model.Current
import com.bartosboth.weatherforecast.data.model.Day
import com.bartosboth.weatherforecast.data.model.Hour
import com.bartosboth.weatherforecast.data.model.Location
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class MainViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private val testScope = TestScope(testDispatcher)

    @Mock
    private lateinit var repository: WeatherRepository

    private lateinit var viewModel: MainViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher) // Set the TestDispatcher as the main dispatcher
        MockitoAnnotations.openMocks(this)
        viewModel = MainViewModel(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // Reset the main dispatcher after tests
    }

    @Test
    fun `getWeatherData updates weatherData and forecastDays on success`() = testScope.runTest {
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
        val mockDataOrException =
            DataOrException<Weather, Boolean, Exception>(data = mockWeather, loading = false, e = null)

        whenever(repository.getWeather(city)).thenReturn(mockDataOrException)

        viewModel.getWeatherData(city)
        testDispatcher.scheduler.advanceUntilIdle() // Ensure all coroutines are executed

        assertEquals(mockWeather, viewModel.weatherData.value.data)
        assertEquals(false, viewModel.weatherData.value.loading)
        assertNull(viewModel.weatherData.value.e)
    }

    @Test
    fun `getWeatherData updates weatherData with exception on failure`() = testScope.runTest {
        val city = "NonexistentCity"
        val mockException = Exception()
        val mockDataOrException = DataOrException<Weather, Boolean, Exception>(
            data = null,
            loading = false,
            e = mockException
        )

        whenever(repository.getWeather(city)).thenReturn(mockDataOrException)

        viewModel.getWeatherData(city)
        testDispatcher.scheduler.advanceUntilIdle()

        assertNull(viewModel.weatherData.value.data)
        assertEquals(false, viewModel.weatherData.value.loading)
        assertEquals(mockException, viewModel.weatherData.value.e)
    }

}
