package com.example.weather.domain.repository

import com.example.weather.data.repository.WeatherRepository
import com.example.weather.domain.entity.Weather
import com.example.weather.retrofit.IWeatherNetwork
import java.io.File

interface IWeatherRepository {
    companion object {
        fun getWeatherRepository(weatherNetwork: IWeatherNetwork): IWeatherRepository {
            return WeatherRepository(weatherNetwork)
        }
    }
    suspend fun getWeatherByCityName(cityName: String): String
    fun getCachedWeather(): String
 }

