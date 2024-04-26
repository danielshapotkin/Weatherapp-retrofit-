package com.example.weather.data

import WeatherNetwork
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weather.data.repository.WeatherRepository
import com.example.weather.domain.repository.IWeatherRepository

class WeatherViewModel : ViewModel() {
    val weatherNetwork = WeatherNetwork()
    private val weatherRepository : IWeatherRepository =  WeatherRepository.getInstance(weatherNetwork)
    val weatherInfo: MutableLiveData<String>  by lazy {
        MutableLiveData<String>()
    }
    fun getCachedValue() {
        val weather = weatherRepository.getCachedWeather()
        weatherInfo.value = weather
    }


}
