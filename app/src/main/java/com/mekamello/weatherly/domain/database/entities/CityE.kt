package com.mekamello.weatherly.domain.database.entities

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "cities")
data class CityE(
    @PrimaryKey val uid: Int,
    @ColumnInfo(name = "name") val name: String
)