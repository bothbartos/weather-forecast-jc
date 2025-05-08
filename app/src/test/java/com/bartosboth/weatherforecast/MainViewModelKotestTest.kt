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
import com.bartosboth.weatherforecast.data.repository.WeatherRepository
import com.bartosboth.weatherforecast.ui.screens.main.MainViewModel
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain

@OptIn(ExperimentalCoroutinesApi::class)
class MainViewModelKotestTest: StringSpec({
    lateinit var viewModel: MainViewModel
    lateinit var repository: WeatherRepository
    val testDispatchers = StandardTestDispatcher()

    val dummyWeather = Weather(
        current = Current(
            condition = Condition(
                icon = "Sunny Icon",
                text = "Sunny"
            ),
            humidity = 1,
            pressure_in = 1.0,
            pressure_mb = 1,
            temp_c = 10.0,
            temp_f = 11.0,
            wind_kph = 10.0,
            wind_mph = 10.0
        ),
        forecast = Forecast(listOf(Forecastday(
            astro = Astro(
                sunrise = "09:00",
                sunset = "19:00"
            ),
            date = "2023-05-01",
            day = Day(
                avghumidity = 1,
                condition = Condition(
                    icon = "Sunny Icon",
                    text = "Sunny"
                ),
                maxtemp_c = 10.0,
                maxtemp_f = 10.0,
                maxwind_kph = 10.0,
                maxwind_mph = 10.0,
                mintemp_c = 10.0,
                mintemp_f = 10.0
            ),
            hour = listOf(Hour(
                condition = Condition(
                    icon = "Sunny Icon",
                    text = "Sunny"
                ),
                humidity = 1,
                temp_c = 10.0,
                temp_f = 10.0,
                time = "01:00",
                pressure_mb = 1
            ),
                Hour(
                    condition = Condition(
                        icon = "Sunny Icon",
                        text = "Sunny"
                    ),
                    humidity = 1,
                    temp_c = 10.0,
                    temp_f = 10.0,
                    time = "02:00",
                    pressure_mb = 1
                ))
        ),
            Forecastday(
                astro = Astro(
                    sunrise = "09:00",
                    sunset = "19:00"
                ),
                date = "2023-05-02",
                day = Day(
                    avghumidity = 1,
                    condition = Condition(
                        icon = "Sunny Icon",
                        text = "Sunny"
                    ),
                    maxtemp_c = 10.0,
                    maxtemp_f = 10.0,
                    maxwind_kph = 10.0,
                    maxwind_mph = 10.0,
                    mintemp_c = 10.0,
                    mintemp_f = 10.0
                ),
                hour = listOf(Hour(
                    condition = Condition(
                        icon = "Sunny Icon",
                        text = "Sunny"
                    ),
                    humidity = 1,
                    temp_c = 10.0,
                    temp_f = 10.0,
                    time = "01:00",
                    pressure_mb = 1
                ),
                    Hour(
                        condition = Condition(
                            icon = "Sunny Icon",
                            text = "Sunny"
                        ),
                        humidity = 1,
                        temp_c = 10.0,
                        temp_f = 10.0,
                        time = "02:00",
                        pressure_mb = 1
                    ))
            ))),
        location = Location(
            country = "Hungary",
            localtime = "10:00",
            name = "Budapest"
        )
    )

    beforeTest {
        Dispatchers.setMain(testDispatchers)
        repository = mockk()
        viewModel = MainViewModel(repository)
    }

    afterTest {
        Dispatchers.resetMain()
    }

    "should get weather data from repository"{
        coEvery { repository.getWeather(any()) } returns DataOrException(
            data = dummyWeather,
            loading = false,
            e = null,
        )

        viewModel.getWeatherData("Budapest")
        testDispatchers.scheduler.advanceUntilIdle()


        viewModel.weatherData.value.data shouldBe dummyWeather
        viewModel.weatherData.value.loading shouldBe false
        viewModel.weatherData.value.e shouldBe null
        viewModel.forecastDays.value shouldBe dummyWeather.forecast.forecastday
    }

    "should handle error when getting weather data"{
        coEvery { repository.getWeather(any()) } returns DataOrException(
            data = null,
            loading = false,
            e = Exception("Error"),
        )
        viewModel.getWeatherData("Budapest")
        testDispatchers.scheduler.advanceUntilIdle()

        viewModel.weatherData.value.data shouldBe null
        viewModel.weatherData.value.loading shouldBe false
        viewModel.weatherData.value.e shouldNotBe null
    }
})