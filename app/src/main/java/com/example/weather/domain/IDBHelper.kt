package com.example.weather.domain

import android.database.sqlite.SQLiteDatabase

interface IDBHelper {
    fun onCreate(db: SQLiteDatabase)
    fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int)
}