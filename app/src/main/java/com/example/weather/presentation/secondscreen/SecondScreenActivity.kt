package com.example.weather.presentation.secondscreen

import ImageDownloader
import DialogFragment
import com.example.weather.data.WeatherViewModel
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.weather.R
import com.example.weather.domain.ImageListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.net.HttpURLConnection
import java.net.URL


class SecondScreenActivity : AppCompatActivity(), ImageListener {
    private lateinit var weatherViewModel: WeatherViewModel
    private lateinit var textView: TextView
    private lateinit var imageView: ImageView
    private lateinit var dialogFragmentButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_secondscreen)

        textView = findViewById(R.id.textView)
        imageView = findViewById(R.id.ImageView)
        dialogFragmentButton = findViewById(R.id.dialogFragmentButton)

        dialogFragmentButton.setOnClickListener {
            val dialogFragment = DialogFragment()
            dialogFragment.setImageListener(this)
            dialogFragment.show(supportFragmentManager, "MyDialog")
        }

        val imageDownloader = ImageDownloader(this, imageView)
        lifecycleScope.launch {
            val bitmap = imageDownloader.downloadImage("https://picsum.photos/200")
            imageDownloader.displayImage(bitmap)
        }

        weatherViewModel = ViewModelProvider(this).get(WeatherViewModel::class.java)
        weatherViewModel.weatherInfo.observe(this) {
            textView.text = it
        }
    }

    override fun onImageSelected(drawable: Drawable) {
        imageView.setImageDrawable(drawable)
    }

    override fun onResume() {
        super.onResume()
        weatherViewModel.getCachedValue()
    }
}
