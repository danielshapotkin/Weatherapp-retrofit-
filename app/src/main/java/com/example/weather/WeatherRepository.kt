package com.example.weather

import com.example.weather.retrofit.IWeatherNetwork
import com.example.weather.retrofit.Weather

interface IWeatherRepository{
    suspend fun getConvertedResult(weather: Weather): String
}
class WeatherRepository() : IWeatherRepository {
    override suspend fun getConvertedResult(weather: Weather): String {
        return "Температура в выбранном городе: " + weather.main.temp.toString()
    }
}