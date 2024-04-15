package com.example.weather.presentation.firstscreen

import WeatherViewModel
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ViewModelProvider
import com.example.weather.data.NetworkUtils
import com.example.weather.PogressDialogUtils
import com.example.weather.R
import com.example.weather.domain.repository.IWeatherRepository
import com.example.weather.retrofit.IWeatherNetwork
import com.example.weather.retrofit.WeatherNetwork
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FirstScreenActivity : AppCompatActivity(), OnClickListener {
    private lateinit var weatherViewModel: WeatherViewModel
    private val weatherNetwork : IWeatherNetwork = WeatherNetwork()
    private val weatherRepository : IWeatherRepository =   IWeatherRepository.getWeatherRepository(weatherNetwork)
    private val networkUtils = NetworkUtils(this@FirstScreenActivity)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val API_KEY = "9cbbdbaf4f0aa3a2b5558f122d628b22"
        val editText = findViewById<EditText>(R.id.editText)
        val textView = findViewById<TextView>(R.id.textView)
        val button = findViewById<Button>(R.id.button)

        weatherViewModel = ViewModelProvider(this).get(WeatherViewModel::class.java)
        weatherViewModel.weatherInfo.observe(this) { weather ->
            textView.text = weather
        }



        button.setOnClickListener {
            val city = editText.text.toString().trim()
            val progressDialogUtils = PogressDialogUtils(this@FirstScreenActivity)

            val job = CoroutineScope(Dispatchers.Main).launch {
                progressDialogUtils.showProgressDialog()

                try {
                    if (!networkUtils.isNetworkAvailable()) {
                        throw Exception("Отсутствует интернет-соединение")
                    }
                    val result = withContext(Dispatchers.IO) {
                         weatherRepository.getWeatherByCityName(city)
                    }
                    weatherViewModel.weatherInfo.value = result
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

    override fun onClick(v: View?) {

    }
}
