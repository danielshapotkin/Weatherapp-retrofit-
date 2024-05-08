package com.example.weather.presentation.firstscreen

import WeatherNetwork
import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import com.example.weather.data.WeatherViewModel
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.core.content.PackageManagerCompat.LOG_TAG
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ViewModelProvider
import com.example.weather.data.NetworkUtils
import com.example.weather.data.ProgressDialogUtils
import com.example.weather.R
import com.example.weather.data.CurrentTime
import com.example.weather.data.DBHelper
import com.example.weather.data.repository.WeatherRepository
import com.example.weather.domain.IDBHelper
import com.example.weather.domain.repository.IWeatherRepository
import com.example.weather.presentation.secondscreen.SecondScreenActivity
import com.example.weather.presentation.thirdscreen.ThirdScreenActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Date

class FirstScreenActivity : AppCompatActivity() {
    private lateinit var weatherViewModel: WeatherViewModel
    private val weatherNetwork: WeatherNetwork = WeatherNetwork()
    private val weatherRepository : IWeatherRepository =   WeatherRepository.getInstance(weatherNetwork)
    private val networkUtils = NetworkUtils(this@FirstScreenActivity)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_firstscreen)
        val editText = findViewById<EditText>(R.id.editText)
        val textView = findViewById<TextView>(R.id.textView)
        val button = findViewById<Button>(R.id.button)
        val secondButton = findViewById<Button>(R.id.secondButton)
        val thirdButton = findViewById<Button>(R.id.thirdButton)
        val checkBdButton = findViewById<Button>(R.id.checkBdButton)
        val bdInfoTW = findViewById<TextView>(R.id.DbInfoTW)
        val deleteBdInfoButton = findViewById<Button>(R.id.deleteBdInfoButton)

        val dbHelper = DBHelper(this)
        val db = dbHelper.writableDatabase

        weatherViewModel = ViewModelProvider(this).get(WeatherViewModel::class.java)
        weatherViewModel.weatherInfo.observe(this) {weather ->
            textView.text = weather
        }

        secondButton.setOnClickListener {
            val intent = Intent(this, SecondScreenActivity::class.java)
            startActivity(intent)
        }

        thirdButton.setOnClickListener {
            val intent = Intent(this, ThirdScreenActivity::class.java)
            startActivity(intent)
        }
        button.setOnClickListener {
            val cv = ContentValues()
            val city = editText.text.toString().trim()
            val progressDialogUtils = ProgressDialogUtils(this@FirstScreenActivity)

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
                    cv.put("city", city)
                    cv.put("temperature", weatherNetwork.getWeatherByCityName(city).main.temp)
                    cv.put("time", CurrentTime().getCurrentTime())
                    db.insert("weathertable", null, cv)
                } catch (e: Exception) {
                    textView.text = when (e.message) {
                        "Отсутствует интернет-соединение" -> "Ошибка, отсутствует интернет-соединение"
                        else -> "Ошибка, город $city не найден"
                    }
                } finally {
                    progressDialogUtils.dismissProgressDialog()
                }

            }

            checkBdButton.setOnClickListener(){
                dbHelper.readBD(db, bdInfoTW)
            }

            deleteBdInfoButton.setOnClickListener(){
                db.delete("weathertable", null, null)
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


