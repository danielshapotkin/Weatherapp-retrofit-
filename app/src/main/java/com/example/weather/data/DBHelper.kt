package com.example.weather.data

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import android.widget.TextView
import com.example.weather.domain.IDBHelper

class DBHelper(context: Context) : SQLiteOpenHelper(context, "DataBase", null, 1), IDBHelper {

    override fun onCreate(db: SQLiteDatabase) {
        Log.d("DataBase", "--- onCreate database ---")
        db.execSQL("create table cityes (id integer primary key autoincrement,"
                + " name text);")
        db.execSQL("create table weathers ("
                + "city_id integer,"
                + "temperature text,"
                + "time text,"
                + "foreign key (city_id) references city(id)"
                + ");")
    }


    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
    }

    override fun readDB(db: SQLiteDatabase): List<String> {
        val dataList = mutableListOf<String>()
        val cursor = db.rawQuery("select cityes.name, weathers.temperature, weathers.time from cityes inner join weathers on cityes.id = weathers.city_id", null)
        if (cursor.moveToFirst()) {
            val cityColIndex = cursor.getColumnIndex("name")
            val temperatureColIndex = cursor.getColumnIndex("temperature")
            val timeColIndex = cursor.getColumnIndex("time")
            do {
                val city = cursor.getString(cityColIndex)
                val temperature = cursor.getString(temperatureColIndex)
                val time = cursor.getString(timeColIndex)
                val itemString = "City = $city, temp = $temperature, time = $time"
                dataList.add(itemString)
            } while (cursor.moveToNext())
        } else {
            dataList.add("0 rows")
        }
        cursor.close()
        return dataList
    }
}