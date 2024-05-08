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
        db.execSQL("create table weathertable ("
                + "city text,"
                + "temperature text,"
                + "time text" + ");")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {

    }

    fun readBD(db: SQLiteDatabase, textView: TextView){
        var stringForTw =  ""
        val c = db.query("weathertable", null, null, null, null, null, null)
        if (c.moveToFirst()) {
            val cityColIndex = c.getColumnIndex("city")
            val temperatureColIndex = c.getColumnIndex("temperature")
            val timeColIndex = c.getColumnIndex("time")
            do {
                stringForTw += "\n City = ${c.getString(cityColIndex)}, temp = ${c.getString(temperatureColIndex)}, email = ${c.getString(timeColIndex)}"
            } while (c.moveToNext())
            textView.text = buildString {
                append(textView.toString())
                append(stringForTw)
            }
        } else {
            textView.text = "0 rows"
        }
        c.close()
    }
}