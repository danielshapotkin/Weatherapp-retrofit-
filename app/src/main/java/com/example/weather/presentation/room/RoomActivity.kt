package com.example.weather.presentation.room

import MyAdapter
import WeatherNetwork
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.asLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weather.data.CurrentTime
import com.example.weather.data.room.RoomDB
import com.example.weather.databinding.ActivityRoomBinding
import com.example.weather.domain.Dao
import com.example.weather.domain.entity.WeatherInfo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RoomActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRoomBinding
    private lateinit var dao: Dao
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRoomBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val db = RoomDB.getDb(this)
        val adapter = MyAdapter()
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this@RoomActivity)

        binding.button2.setOnClickListener(){
            CoroutineScope(Dispatchers.Main).launch {
                val city = binding.editText.text.trim().toString()
                val temp = WeatherNetwork().getWeatherByCityName(city).main.temp
                val time = CurrentTime().getCurrentTime()
                val weatherInfo = WeatherInfo(null, city, temp, time)
                //сохраняем в базу данных
                dao = db.getDao()
                withContext(Dispatchers.IO){
                    dao.insertItem(weatherInfo)
                }
                dao.getAllItems().asLiveData().observe(this@RoomActivity){
                    val list = mutableListOf<String>()
                    it.forEach {
                        val text = "Id: ${it.id} City: ${it.city} Temp: ${it.temp} Time: ${it.time}"
                        list.add(text)
                    }
                    adapter.setData(list)
                }
            }
            }
        }

    }
