package com.example.weather.domain

interface IListener {
    fun onCityUpdated(city: String)
}