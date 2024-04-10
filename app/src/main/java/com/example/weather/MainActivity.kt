package com.example.weather

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.weather.retrofit.IWeatherApi
import com.example.weather.retrofit.WeatherNetwork
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    private val weatherNetwork = WeatherNetwork()
    private val weatherRepository = WeatherRepository()
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


        val weatherApi = retrofit.create(IWeatherApi::class.java)


        button.setOnClickListener {
            val city = editText.text.toString()


            val deffered : Deferred<String> = CoroutineScope(Dispatchers.IO).async { "hb" }
            val job: Job = CoroutineScope(Dispatchers.IO).launch {
                try {
                    val weather = weatherNetwork.getWeatherByCityName(city)
                    val result = weatherRepository.getConvertedResult(weather)

                    withContext(Dispatchers.Main) {
                        textView.text = result
                    }
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        textView.text = "Ошибка получения данных"
                    }
                }
            }
            runBlocking {
                job.join()
                //все корутины завершены
            }

        }
    }
}
val str: String = "dcw"
