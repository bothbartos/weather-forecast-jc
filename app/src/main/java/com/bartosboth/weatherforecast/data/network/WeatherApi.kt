package com.bartosboth.weatherforecast.data.network

import com.bartosboth.weatherforecast.data.model.Weather
import com.bartosboth.weatherforecast.utils.Constants
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Singleton


@Singleton
interface WeatherApi {
    @GET("forecast.json?")
    suspend fun getWeather(
        @Query("key") key: String = Constants.API_KEY,
        @Query("q") query: String,
        @Query("days") days: String = "3",
        @Query("aqi") aqi: String = "no",
        @Query("alerts") alerts: String = "no"
    ): Weather
}