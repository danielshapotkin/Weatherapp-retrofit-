package com.example.weather.domain

import android.graphics.Bitmap

interface IImageDownloader {
    suspend fun downloadImage(urlString: String?): Bitmap?
    suspend fun saveImageToExternalStorage(bitmap: Bitmap)
}