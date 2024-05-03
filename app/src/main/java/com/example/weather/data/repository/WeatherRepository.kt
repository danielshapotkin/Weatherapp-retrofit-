package com.example.weather.data.repository

import WeatherNetwork
import com.example.weather.domain.repository.IWeatherRepository
import com.example.weather.domain.entity.Weather


class WeatherRepository(private val weatherNetwork: WeatherNetwork) : IWeatherRepository {
    companion object {

        @Volatile
        private var instance: WeatherRepository? = null

        fun getInstance(weatherNetwork: WeatherNetwork):WeatherRepository{

        if (instance == null) {
            synchronized(this) {
                if (instance == null) {
                    instance = WeatherRepository(weatherNetwork)
                }
            }
        }
        return instance!!
    }
    }
    // Приватная переменная для кэширования погодных данных
    private var cachedWeather: Weather? = null

    // Приватная функция для преобразования объекта Weather в строковый формат
    private fun getConvertedResult(weather: Weather): String {
        return "Температура в выбранном городе: " + weather.main.temp.toString()
    }

    // Переопределенный метод интерфейса для получения погоды по имени города
    override suspend fun getWeatherByCityName(cityName: String): String {
        // Получение погодных данных из сети и сохранение в кэше
        cachedWeather = weatherNetwork.getWeatherByCityName(cityName)
        // Преобразование и возврат результата
        return cachedWeather?.let { getConvertedResult(it) } ?: "City not found"
    }

    // Метод для получения кэшированных погодных данных
    override fun getCachedWeather(): String {
        // Преобразование и возврат кэшированных данных или сообщения об отсутствии данных
        return cachedWeather?.let { getConvertedResult(it) } ?: "Cached value don't found"
    }
}
