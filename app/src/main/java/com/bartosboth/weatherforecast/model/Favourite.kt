package com.bartosboth.weatherforecast.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favourite_table")
data class Favourite(
    @PrimaryKey
    @ColumnInfo(name = "city")
    val city: String,
    @ColumnInfo(name = "country")
    val country: String
)
