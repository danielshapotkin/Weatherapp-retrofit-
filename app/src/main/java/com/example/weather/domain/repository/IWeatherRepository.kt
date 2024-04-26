package com.example.weather.domain.repository

import WeatherNetwork
import com.example.weather.data.repository.WeatherRepository
import java.io.File

interface IWeatherRepository {
    companion object {
        fun getWeatherRepository(weatherNetwork: WeatherNetwork): IWeatherRepository {
            return WeatherRepository(weatherNetwork)
        }
    }
    suspend fun getWeatherByCityName(cityName: String): String
    fun getCachedWeather(): String
 }

