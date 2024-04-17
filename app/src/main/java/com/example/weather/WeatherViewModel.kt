import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weather.data.repository.WeatherRepositoryProvider
import com.example.weather.domain.repository.IWeatherRepository
import com.example.weather.retrofit.IWeatherNetwork
import com.example.weather.retrofit.WeatherNetwork

class WeatherViewModel : ViewModel() {
    private val weatherRepository : IWeatherRepository =  WeatherRepositoryProvider.weatherRepository
    val weatherInfo: MutableLiveData<String>  by lazy {
        MutableLiveData<String>()
    }
    fun getCachedValue() {
        val weather = weatherRepository.getCachedWeather()
        weatherInfo.value = weather
    }


}
