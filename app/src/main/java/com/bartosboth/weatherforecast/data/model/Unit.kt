package com.bartosboth.weatherforecast.data.model

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "settings_tbl")
data class Unit(
    @PrimaryKey val id: Int = 0,
    @ColumnInfo(name = "is_imperial") val isImperial: Boolean
)
