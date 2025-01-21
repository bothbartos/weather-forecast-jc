package com.bartosboth.weatherforecast.repository

import com.bartosboth.weatherforecast.data.WeatherDao
import com.bartosboth.weatherforecast.model.Favourite
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class FavouriteRepository @Inject constructor(private val weatherDao: WeatherDao){
    fun getFavourites(): Flow<List<Favourite>> = weatherDao.getFavourites()
    suspend fun getFavouriteByCity(city: String): Favourite = weatherDao.getFavouriteByCity(city)
    suspend fun insertFavourite(favourite: Favourite) = weatherDao.insertFavourite(favourite)
    suspend fun updateFavourite(favourite: Favourite) = weatherDao.updateFavourite(favourite)
    suspend fun deleteFavourite(favourite: Favourite) = weatherDao.deleteFavourite(favourite)
}
