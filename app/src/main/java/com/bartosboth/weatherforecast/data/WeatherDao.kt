package com.bartosboth.weatherforecast.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bartosboth.weatherforecast.model.Favourite
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherDao {
    @Query("SELECT * from favourite_table")
    fun getFavourites(): Flow<List<Favourite>>

    @Query("SELECT * from favourite_table where city =:city")
    suspend fun getFavouriteByCity(city: String): Favourite

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavourite(favourite: Favourite)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateFavourite(favourite: Favourite)

    @Delete
    suspend fun deleteFavourite(favourite: Favourite)

}