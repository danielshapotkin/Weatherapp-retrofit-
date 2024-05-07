package com.example.weather.presentation.thirdscreen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.weather.R
import com.example.weather.domain.IListener
import com.example.weather.domain.repository.ISwitchListener

class ThirdScreenActivity : AppCompatActivity(), IListener, ISwitchListener {
    private val fragment2 = supportFragmentManager.findFragmentById(R.id.fragment_container2) as? Fragment2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_third_screen)

        val fragmentTransaction = supportFragmentManager.beginTransaction()

        val fragment1 = Fragment1()
        fragment1.setListener(this)
        fragment1.setSwitchListener(this)
        val fragment2 = Fragment2()


        fragmentTransaction.replace(R.id.fragment_container1, fragment1)

        fragmentTransaction.replace(R.id.fragment_container2, fragment2)

        fragmentTransaction.commit()
    }


    override fun onCityUpdated(city: String) {
        fragment2?.onCityUpdated(city)
    }

    override fun onChecked() {
        fragment2?.onChecked()
    }

    override fun onUnChecked() {
        fragment2?.onUnChecked()
    }


}