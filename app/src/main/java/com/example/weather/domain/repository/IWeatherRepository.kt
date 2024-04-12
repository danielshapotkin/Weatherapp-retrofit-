package com.example.weather.domain.repository

import com.example.weather.data.repository.WeatherRepository
import com.example.weather.retrofit.IWeatherNetwork

interface IWeatherRepository {
    companion object {
        fun getWeatherRepository(weatherNetwork: IWeatherNetwork): IWeatherRepository {
            return WeatherRepository(weatherNetwork)
        }
    }
    suspend fun getWeatherByCityName(cityName: String): String
    fun getCachedWeather(): String
}
