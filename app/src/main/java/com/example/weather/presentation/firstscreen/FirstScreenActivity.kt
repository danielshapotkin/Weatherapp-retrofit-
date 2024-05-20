package com.example.weather.presentation.firstscreen

import MyAdapter
import WeatherNetwork
import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.CheckBox
import android.widget.CheckedTextView
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weather.R
import com.example.weather.data.CurrentTime
import com.example.weather.data.DBHelper
import com.example.weather.data.NetworkUtils
import com.example.weather.data.ProgressDialogUtils
import com.example.weather.data.WeatherViewModel
import com.example.weather.data.repository.WeatherRepository
import com.example.weather.domain.repository.IWeatherRepository
import com.example.weather.presentation.room.RoomActivity
import com.example.weather.presentation.secondscreen.SecondScreenActivity
import com.example.weather.presentation.thirdscreen.ThirdScreenActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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
        val btnRoom = findViewById<Button>(R.id.btnRoom)
        val thirdButton = findViewById<Button>(R.id.thirdButton)
        val deleteBdInfoButton = findViewById<Button>(R.id.btnDelete)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        val checkBox = findViewById<CheckBox>(R.id.checkBox)
        val adapter = MyAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this@FirstScreenActivity)
        val dbHelper = DBHelper(this)
        val db = dbHelper.writableDatabase // экземпляр базы данных

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
            var cv = ContentValues()
            val city = editText.text.toString().trim()
            val progressDialogUtils = ProgressDialogUtils(this@FirstScreenActivity)
            CoroutineScope(Dispatchers.Main).launch {
                progressDialogUtils.showProgressDialog()

                try {
                    if (!networkUtils.isNetworkAvailable()) {
                        throw Exception("Отсутствует интернет-соединение")
                    }
                    val result = withContext(Dispatchers.IO) {
                        weatherRepository.getWeatherByCityName(city)
                    }
                    weatherViewModel.weatherInfo.value = result
                    cv.put("name", city)
                    val id = db.insert("cityes", null, cv)
                    cv = ContentValues()
                    cv.put("city_id", id)
                    cv.put("temperature", weatherNetwork.getWeatherByCityName(city).main.temp)
                    cv.put("time", CurrentTime().getCurrentTime())
                    cv.put("favorite", if (checkBox.isChecked) 1 else 0)
                    db.insert("weathers", null, cv)
                    adapter.setData(dbHelper.readDB(db))
                } catch (e: Exception) {
                    textView.text = when (e.message) {
                        "Отсутствует интернет-соединение" -> "Ошибка, отсутствует интернет-соединение"
                        else -> "Ошибка, город $city не найден"
                    }
                } finally {
                    progressDialogUtils.dismissProgressDialog()
                }
            }
        }

            deleteBdInfoButton.setOnClickListener(){
                    db.delete("cityes", null, null)
                    db.delete("weathers", null, null)
                    adapter.setData(emptyList())
            }

            btnRoom.setOnClickListener(){
                val intent = Intent(this, RoomActivity::class.java)
                startActivity(intent)
            }


        }

    }





