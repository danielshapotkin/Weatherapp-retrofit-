package com.example.weather.data.repository

import com.example.weather.domain.repository.IWeatherRepository
import com.example.weather.retrofit.IWeatherNetwork
import com.example.weather.domain.entity.Weather
class WeatherRepository(val weatherNetwork : IWeatherNetwork) : IWeatherRepository {
    private var cachedWeather : Weather? = null
    private fun getConvertedResult(weather: Weather): String {
        return "Температура в выбранном городе: " + weather.main.temp.toString()
    }

    override suspend fun getWeatherByCityName(cityName: String): String {
        cachedWeather = weatherNetwork.getWeatherByCityName(cityName)
        return cachedWeather?.let { getConvertedResult(it) } ?: "City not found"
    }
    override fun getCachedWeather(): String {
        return cachedWeather?.let { getConvertedResult(it) } ?: "Cached value don't found"
    }

}
