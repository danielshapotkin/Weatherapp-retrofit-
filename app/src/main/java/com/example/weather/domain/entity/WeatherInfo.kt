package com.example.weather.domain.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity (tableName = "weathers")
data class WeatherInfo(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    @ColumnInfo("city")
    var city: String,
    @ColumnInfo("temp")
    var temp: Double,
    @ColumnInfo("time")
    var time: String
)