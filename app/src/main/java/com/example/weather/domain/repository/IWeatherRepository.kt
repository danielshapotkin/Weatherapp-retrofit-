package com.example.weather.domain.repository

import WeatherNetwork
import com.example.weather.data.repository.WeatherRepository
import java.io.File

interface IWeatherRepository {
    suspend fun getWeatherByCityName(cityName: String): String
    fun getCachedWeather(): String
 }

