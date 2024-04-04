package com.example.weather

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.weather.retrofit.WeatherApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val API_KEY = "9cbbdbaf4f0aa3a2b5558f122d628b22"
        val editText = findViewById<EditText>(R.id.editText)
        val textView = findViewById<TextView>(R.id.textView)
        val button = findViewById<Button>(R.id.button)


        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/data/2.5/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()


        val weatherApi = retrofit.create(WeatherApi::class.java)


        button.setOnClickListener(){
            CoroutineScope(Dispatchers.IO).launch {
                val city = editText.text.trim().toString()
                val weather = weatherApi.getWeatherByCityName(city, API_KEY, "metric", "ru")
                runOnUiThread(){
                    textView.text = weather.main.temp.toString() +"\n" +
                            weather.weatherDetail[0].description + "\n" +
                            weather.weatherDetail[0].main + "\n" +
                            weather.clouds.toString() + "\n" +
                            weather.timezone.toString()

                }
            }
        }
    }
}
