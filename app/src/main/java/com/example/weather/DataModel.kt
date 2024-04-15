import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class WeatherViewModel : ViewModel() {
    val weatherInfo: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }
}
