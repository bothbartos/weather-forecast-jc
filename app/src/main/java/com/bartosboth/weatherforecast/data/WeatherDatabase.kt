package com.bartosboth.weatherforecast.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.bartosboth.weatherforecast.model.Favourite
import com.bartosboth.weatherforecast.model.Unit

@Database(entities = [Favourite::class, Unit::class], version = 2, exportSchema = false)
abstract class WeatherDatabase: RoomDatabase() {

    abstract fun weatherDao(): WeatherDao

}