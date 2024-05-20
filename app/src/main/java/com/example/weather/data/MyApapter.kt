import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.weather.R
import com.example.weather.domain.entity.WeatherInfo

class MyAdapter : RecyclerView.Adapter<MyAdapter.ViewHolder>() {

    private val dataList = mutableListOf<String>()

    // Метод для установки нового списка данных
    fun setData(newDataList: List<String>) {
        dataList.clear()
        dataList.addAll(newDataList)
        notifyDataSetChanged()
    }

    // Создаем новый объект ViewHolder всякий раз, когда RecyclerView нуждается в этом.
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
        return ViewHolder(view)
    }

    // Связывает данные с ViewHolder в указанной позиции в RecyclerView.
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(dataList[position])
    }

    // Возвращает размер данных (списка), отображаемых RecyclerView.
    override fun getItemCount(): Int {
        return dataList.size
    }

    // Определяет представление для элемента данных в RecyclerView.
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textView: TextView = itemView.findViewById(R.id.textView)
        fun bind(data: String) {
            textView.text = data
        }
    }
}
