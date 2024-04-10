package com.example.weather.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import retrofit2.http.GET
import retrofit2.http.Query

interface IWeatherApi {
    @GET("weather")
    suspend fun getWeatherByCityName(
        @Query("q") city: String,
        @Query("appid") apiKey: String,
        @Query("units") units: String,
        @Query("lang") lang: String
    ): Weather

}
interface IWeatherNetwork{
    suspend fun getWeatherByCityName(city: String): Weather
}
 class WeatherNetwork:IWeatherNetwork {
    private val weatherApi: IWeatherApi
     private val API_KEY = "9cbbdbaf4f0aa3a2b5558f122d628b22"
    private val METRIC = "metric"
    private val LOCALE = "ru"

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/data/2.5/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        weatherApi = retrofit.create(IWeatherApi::class.java)
    }

     override suspend fun getWeatherByCityName(city: String): Weather {
         return weatherApi.getWeatherByCityName(city, API_KEY, METRIC, LOCALE)
     }
}