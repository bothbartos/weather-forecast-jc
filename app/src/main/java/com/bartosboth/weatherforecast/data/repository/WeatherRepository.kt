package com.bartosboth.weatherforecast.data.repository

import android.util.Log
import com.bartosboth.weatherforecast.data.DataOrException
import com.bartosboth.weatherforecast.data.model.Weather
import com.bartosboth.weatherforecast.data.network.WeatherApi
import javax.inject.Inject


class WeatherRepository @Inject constructor(private val weatherApi: WeatherApi){

    suspend fun getWeather(city:String ): DataOrException<Weather, Boolean, Exception> {
        val response = try{
            weatherApi.getWeather(query = city)

        }catch (e: Exception){
            return DataOrException(e = e, loading = false)
        }
        return DataOrException(data = response, loading = false)
    }
}