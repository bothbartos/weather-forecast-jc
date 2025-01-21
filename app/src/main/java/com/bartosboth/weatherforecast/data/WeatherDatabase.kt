package com.bartosboth.weatherforecast.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.bartosboth.weatherforecast.model.Favourite

@Database(entities = [Favourite::class], version = 1, exportSchema = false)
abstract class WeatherDatabase: RoomDatabase() {

    abstract fun weatherDao(): WeatherDao

}