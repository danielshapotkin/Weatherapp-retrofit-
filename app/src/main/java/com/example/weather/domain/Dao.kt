package com.example.weather.domain

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.weather.domain.entity.WeatherInfo
import kotlinx.coroutines.flow.Flow

@Dao
interface Dao {
    @Insert
    fun insertItem(item: WeatherInfo)
    @Query("SELECT * FROM weathers")
    fun getAllItems() : Flow<List<WeatherInfo>>
}