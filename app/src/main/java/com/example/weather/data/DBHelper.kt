package com.example.weather.data

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import android.widget.TextView
import com.example.weather.domain.IDBHelper

class DBHelper(context: Context) : SQLiteOpenHelper(context, "myDB", null, 1), IDBHelper {

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

    override fun readDB(db: SQLiteDatabase, textView: TextView) {
        var stringForTw = ""
        textView.text = ""
        val cursor = db.rawQuery("select cityes.name, weathers.temperature, weathers.time from cityes inner join weathers on cityes.id = weathers.city_id", null)
        if (cursor.moveToFirst()) {
            val cityColIndex = cursor.getColumnIndex("name")
            val temperatureColIndex = cursor.getColumnIndex("temperature")
            val timeColIndex = cursor.getColumnIndex("time")
            do {
                stringForTw += "\n City = ${cursor.getString(cityColIndex)}, temp = ${cursor.getString(temperatureColIndex)}, time = ${cursor.getString(timeColIndex)}"
            } while (cursor.moveToNext())
            textView.text = buildString {
                append(textView.text.toString())
                append(stringForTw)
            }
        } else {
            textView.text = "0 rows"
        }
        cursor.close()
    }
}