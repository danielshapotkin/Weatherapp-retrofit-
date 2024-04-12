package com.example.weather

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
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
            val city = editText.text.toString().trim()
            val networkUtils = NetworkUtils(this@MainActivity)
            val progressDialogUtils = PogressDialogUtils(this@MainActivity)

            val job = CoroutineScope(Dispatchers.Main).launch {
                progressDialogUtils.showProgressDialog()

                try {
                    if (!networkUtils.isNetworkAvailable()) {
                        throw Exception("Отсутствует интернет-соединение")
                    }

                    val deferred = CoroutineScope(Dispatchers.IO).async {
                        val weather = weatherNetwork.getWeatherByCityName(city)
                        val result = weatherRepository.getConvertedResult(weather)
                        result
                    }

                    val result = deferred.await()

                    textView.text = result
                } catch (e: Exception) {
                    textView.text = when (e.message) {
                        "Отсутствует интернет-соединение" -> "Ошибка, отсутствует интернет-соединение"
                        else -> "Ошибка, город $city не найден"
                    }
                } finally {
                    progressDialogUtils.dismissProgressDialog()
                }
            }

            // Отмена корутины при уничтожении активности (например, при закрытии приложения)
            lifecycle.addObserver(object : LifecycleObserver {
                @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
                fun onDestroy() {
                    job.cancel()
                }
            })
        }

    }
}
