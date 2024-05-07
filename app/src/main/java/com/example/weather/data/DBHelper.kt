package com.example.weather.data

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.example.weather.domain.IDBHelper

class DBHelper(context: Context) : SQLiteOpenHelper(context, "myDB", null, 1), IDBHelper {

    override fun onCreate(db: SQLiteDatabase) {
        Log.d("DataBase", "--- onCreate database ---")
        db.execSQL("create table mytable ("
                + "id integer primary key autoincrement,"
                + "city text,"
                + "temperature text,"
                + "date text" + ");")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {

    }
}