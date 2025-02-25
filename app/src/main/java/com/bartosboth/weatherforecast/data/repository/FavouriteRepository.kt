package com.bartosboth.weatherforecast.data.repository

import com.bartosboth.weatherforecast.data.WeatherDao
import com.bartosboth.weatherforecast.data.model.Favourite
import com.bartosboth.weatherforecast.data.model.Unit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject


class FavouriteRepository @Inject constructor(private val weatherDao: WeatherDao){

    //Favourite

    fun getFavourites(): Flow<List<Favourite>> = weatherDao.getFavourites()
    suspend fun getFavouriteByCity(city: String): Favourite = weatherDao.getFavouriteByCity(city)
    suspend fun insertFavourite(favourite: Favourite) = weatherDao.insertFavourite(favourite)
    suspend fun updateFavourite(favourite: Favourite) = weatherDao.updateFavourite(favourite)
    suspend fun deleteFavourite(favourite: Favourite) = weatherDao.deleteFavourite(favourite)

    //Unit

    fun getUnitSetting(): Flow<Boolean> = weatherDao.getUnitSetting()
        .map { it?.isImperial ?: false }

    suspend fun setUnitSetting(isImperial: Boolean) {
        val unitSetting = Unit(isImperial = isImperial)
        weatherDao.insertUnitSetting(unitSetting)
    }
}
