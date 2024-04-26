import com.example.weather.domain.entity.Weather
import com.example.weather.retrofit.IWeatherApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class WeatherNetwork: IWeatherNetwork {
    private val weatherApi: IWeatherApi
    private val API_KEY = "9cbbdbaf4f0aa3a2b5558f122d628b22"
    private val METRIC = "metric"
    private val LOCALE = "ru"

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/data/2.5/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        weatherApi = retrofit.create(IWeatherApi::class.java)
    }

    override suspend fun getWeatherByCityName(city: String): Weather {
        return weatherApi.getWeatherByCityName(city, API_KEY, METRIC, LOCALE)
    }
}