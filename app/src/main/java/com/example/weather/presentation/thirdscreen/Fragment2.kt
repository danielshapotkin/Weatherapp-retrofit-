package com.example.weather.presentation.thirdscreen

import WeatherNetwork
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.example.weather.R
import com.example.weather.data.NetworkUtils
import com.example.weather.data.ProgressDialogUtils
import com.example.weather.data.WeatherViewModel
import com.example.weather.data.repository.WeatherRepository
import com.example.weather.domain.repository.IWeatherRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class Fragment2 : Fragment() {
    private lateinit var textView: TextView
    private lateinit var resultTextView: TextView
    private lateinit var weatherViewModel: WeatherViewModel
    private val weatherNetwork: WeatherNetwork = WeatherNetwork()
    private val weatherRepository : IWeatherRepository =   WeatherRepository.getInstance(weatherNetwork)




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_2, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        textView = view.findViewById(R.id.textView)
        resultTextView = view.findViewById(R.id.resultTextView)
        weatherViewModel = ViewModelProvider(this).get(WeatherViewModel::class.java)
        weatherViewModel.weatherInfo.observe(viewLifecycleOwner) {weather ->
            resultTextView.text = weather
        }
    }

    fun updateTextView (city: String){
        textView.text = city
    }

    fun getWeather(city: String){
        CoroutineScope(Dispatchers.Main).launch {
            val pd = ProgressDialogUtils(requireContext())
            pd.showProgressDialog()
            try {
                if (!NetworkUtils(requireContext()).isNetworkAvailable()) {
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
                pd.dismissProgressDialog()
            }
        }
    }

}