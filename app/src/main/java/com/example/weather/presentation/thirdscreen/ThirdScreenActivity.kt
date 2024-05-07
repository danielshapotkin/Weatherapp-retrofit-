package com.example.weather.presentation.thirdscreen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.weather.R
import com.example.weather.domain.IListener

class ThirdScreenActivity : AppCompatActivity(), IListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_third_screen)

        val fragmentTransaction = supportFragmentManager.beginTransaction()

        val fragment1 = Fragment1()
        val fragment2 = Fragment2()
        fragment1.setListener(this)

        fragmentTransaction.replace(R.id.fragment_container1, fragment1)

        fragmentTransaction.replace(R.id.fragment_container2, fragment2)

        fragmentTransaction.commit()
    }

    override fun onCityUpdated(city: String) {
        val fragment2 = supportFragmentManager.findFragmentById(R.id.fragment_container2) as? Fragment2
        fragment2?.onCityUpdated(city)
    }
}