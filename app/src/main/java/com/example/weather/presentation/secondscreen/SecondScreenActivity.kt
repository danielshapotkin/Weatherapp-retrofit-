import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.weather.DataModel
import com.example.weather.R
import com.example.weather.data.repository.WeatherRepository
import com.example.weather.retrofit.WeatherNetwork

class SecondScreenActivity : AppCompatActivity() {
    private val dataModel = DataModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_secondscreen)
        val textView = findViewById<TextView>(R.id.textView)

        dataModel.weatherInfo.observe(this) { weatherInfo ->
            // Обновление UI при изменении сообщения
            textView.text = weatherInfo
        }



    }
}