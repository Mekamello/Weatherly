package com.mekamello.weatherly.domain.database.entities

import android.arch.persistence.room.*

@Entity(
    tableName = "weathers",
    foreignKeys = [ForeignKey(
        entity = CityE::class,
        parentColumns = ["uid"],
        childColumns = ["city_id"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class WeatherInfoE(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "city_id") val cid: Int,
    @ColumnInfo(name = "date") val date: Long,
    @Embedded(prefix = "t") val temperature: TemperatureE,
    @Embedded(prefix = "w") val weather: WeatherE
)

data class TemperatureE(
    @ColumnInfo(name = "temp") val temp: Float,
    @ColumnInfo(name = "min") val min: Float,
    @ColumnInfo(name = "max") val max: Float
)

data class WeatherE(
    @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name = "main") val main: String,
    @ColumnInfo(name = "description") val description: String
)