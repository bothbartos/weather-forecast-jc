package com.bartosboth.weatherforecast.repository

import android.util.Log
import com.bartosboth.weatherforecast.data.DataOrException
import com.bartosboth.weatherforecast.model.Weather
import com.bartosboth.weatherforecast.network.WeatherApi
import javax.inject.Inject


class WeatherRepository @Inject constructor(private val weatherApi: WeatherApi){

    suspend fun getWeather(city:String ): DataOrException<Weather, Boolean, Exception> {
        val response = try{
            weatherApi.getWeather(query = city)

        }catch (e: Exception){
            Log.d("TAG", "getWeather: $e")
            return DataOrException(e = e)
        }

        Log.d("API_RESPONSE", response.toString())
        return DataOrException(data = response)
    }
}