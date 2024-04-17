package com.example.weather.data.repository

import com.example.weather.domain.repository.IWeatherRepository
import com.example.weather.retrofit.IWeatherNetwork
import com.example.weather.retrofit.WeatherNetwork

object WeatherRepositoryProvider {
    private val weatherNetwork: IWeatherNetwork = WeatherNetwork()
    val weatherRepository: IWeatherRepository = WeatherRepository(weatherNetwork)
}
