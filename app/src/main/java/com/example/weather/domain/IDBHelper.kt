package com.example.weather.domain

import android.database.sqlite.SQLiteDatabase
import android.widget.TextView

interface IDBHelper {

    fun onCreate(db: SQLiteDatabase)
    fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int)
    fun readDB(db: SQLiteDatabase): List<String>
}