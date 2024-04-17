package com.example.weather.presentation.secondscreen


import WeatherViewModel
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.weather.R
import com.example.weather.data.repository.WeatherRepository
import com.example.weather.retrofit.WeatherNetwork
import com.squareup.picasso.Picasso

class SecondScreenActivity : AppCompatActivity() {
    private lateinit var weatherViewModel: WeatherViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_secondscreen)
        val textView = findViewById<TextView>(R.id.textView)
        val imageView = findViewById<ImageView>(R.id.ImageView)
        val imageURL = "https://picsum.photos/200"
        Picasso.get()
            .load(imageURL)
            .into(imageView)

        weatherViewModel = ViewModelProvider(this).get(WeatherViewModel::class.java)
        weatherViewModel.weatherInfo.observe(this) {
            textView.text = it
        }

    }

    override fun onResume() {
        super.onResume()
        weatherViewModel.getCachedValue()
        val imageUrl = "https://example.com/image.jpg"  // Замените на ваш URL изображения


    }
}