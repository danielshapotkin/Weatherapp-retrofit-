package com.example.weather.data.room

import android.content.Context
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.weather.domain.entity.WeatherInfo

@Database(entities = [WeatherInfo::class], version = 1)
abstract class RoomDB(): RoomDatabase() {
    abstract fun getDao(): com.example.weather.domain.Dao
companion object{
    fun getDb(context: Context): RoomDB{
         return Room.databaseBuilder(
             context.applicationContext,
             RoomDB::class.java,
             "roomdb.db"
         ).build()
        }
    }
}