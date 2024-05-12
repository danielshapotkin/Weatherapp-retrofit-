package com.example.weather.presentation.thirdscreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.widget.SwitchCompat
import androidx.fragment.app.Fragment
import com.example.weather.R
import com.example.weather.domain.IListener

class Fragment1 : Fragment() {
    private lateinit var listener: IListener

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_1, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val editText = view.findViewById<EditText>(R.id.editText1)
        val button = view.findViewById<Button>(R.id.button1)





        button.setOnClickListener(){
                val city = editText.text.toString().trim()
                listener.onCityUpdated(city)
        }
    }

    fun setListener(listener: IListener){
        this.listener = listener
    }






}