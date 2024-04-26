import com.example.weather.domain.entity.Weather

interface IWeatherNetwork{
    suspend fun getWeatherByCityName(city: String): Weather?
}
