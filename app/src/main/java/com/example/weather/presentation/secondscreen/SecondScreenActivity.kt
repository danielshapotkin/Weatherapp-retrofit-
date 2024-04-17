package com.example.weather.presentation.secondscreen


import WeatherViewModel
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.weather.R
import com.example.weather.data.repository.WeatherRepository
import com.example.weather.retrofit.WeatherNetwork
import com.squareup.picasso.Picasso
import java.io.File
import java.io.FileOutputStream
import java.net.HttpURLConnection
import java.net.URL

class SecondScreenActivity : AppCompatActivity() {
    private lateinit var weatherViewModel: WeatherViewModel
    private lateinit var imageView: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_secondscreen)
        val textView = findViewById<TextView>(R.id.textView)
        imageView = findViewById<ImageView>(R.id.ImageView)
        val imageURL = "https://picsum.photos/200"
//        Picasso.get()
//            .load(imageURL)
//            .into(imageView)
DownloadImageTask().execute(imageURL)
        weatherViewModel = ViewModelProvider(this).get(WeatherViewModel::class.java)
        weatherViewModel.weatherInfo.observe(this) {
            textView.text = it
        }

    }
    override fun onResume() {
        super.onResume()
        weatherViewModel.getCachedValue()
    }

    inner class DownloadImageTask : AsyncTask<String, Void, Bitmap?>() {

        override fun doInBackground(vararg urls: String?): Bitmap? {
            val imageUrl = urls[0]
            var bitmap: Bitmap? = null

            try {
                val url = URL(imageUrl)
                val connection = url.openConnection() as HttpURLConnection
                connection.doInput = true
                connection.connect()
                val inputStream = connection.inputStream
                bitmap = BitmapFactory.decodeStream(inputStream)
            } catch (e: Exception) {
                e.printStackTrace()
            }

            return bitmap
        }

        override fun onPostExecute(result: Bitmap?) {
            super.onPostExecute(result)

            result?.let {
                imageView.setImageBitmap(it)
                saveImageToExternalStorage(it)
            }
        }
    }

    private fun saveImageToExternalStorage(bitmap: Bitmap) {
        val directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
        val fileName = "image.jpg"
        val file = File(directory, fileName)

        var fileOutputStream: FileOutputStream? = null
        try {
            fileOutputStream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream)
            fileOutputStream.flush()
            fileOutputStream.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        fileOutputStream?.let {
            it.flush()
            it.close()
        }
    }
}