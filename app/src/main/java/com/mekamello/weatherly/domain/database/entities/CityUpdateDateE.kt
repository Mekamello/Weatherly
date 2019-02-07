package com.mekamello.weatherly.domain.database.entities

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.PrimaryKey

@Entity(
    tableName = "updates",
    foreignKeys = [ForeignKey(
        entity = CityE::class,
        parentColumns = ["uid"],
        childColumns = ["cid"],
        onDelete = ForeignKey.CASCADE
    )]
)
class CityUpdateDateE(
    @PrimaryKey val cid: Int,
    @ColumnInfo(name = "date") val date: Long
)