package com.example.weather.presentation.thirdscreen

import WeatherNetwork
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.example.weather.R
import com.example.weather.data.NetworkUtils
import com.example.weather.data.ProgressDialogUtils
import com.example.weather.data.repository.WeatherRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class Fragment1 : Fragment() {
    private var fragment2 : Fragment2? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_1, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val editText = view.findViewById<EditText>(R.id.editText1)
        val button = view.findViewById<Button>(R.id.button1)

        button.setOnClickListener(){
                val city = editText.text.toString().trim()
                fragment2?.updateTextView(city)
        }
    }


    fun setFragment2(fr: Fragment2){
        fragment2 = fr
    }

}