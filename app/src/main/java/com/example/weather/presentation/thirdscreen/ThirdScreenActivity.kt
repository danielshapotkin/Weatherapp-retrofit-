package com.example.weather.presentation.thirdscreen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.weather.R
import com.example.weather.domain.ICityListener

class ThirdScreenActivity : AppCompatActivity(), ICityListener {

    private var fragment1 : Fragment1? = null
    private var fragment2 : Fragment2? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_third_screen)
        fragment1 = supportFragmentManager.findFragmentById(R.id.fragment_container1) as? Fragment1
        fragment2 = supportFragmentManager.findFragmentById(R.id.fragment_container2) as? Fragment2

        if (fragment1 == null || fragment2 == null) {
            val fragmentTransaction = supportFragmentManager.beginTransaction()
            if (fragment1 == null) {
                fragment1 = Fragment1().apply {
                    fragmentTransaction.replace(R.id.fragment_container1, this)
                }
            }
            if (fragment2 == null) {
                fragment2 = Fragment2().apply {
                    fragmentTransaction.replace(R.id.fragment_container2, this)
                }
            }
            fragmentTransaction.commit()
        }
        fragment1?.setActivity(this)
    }

    override fun onCityUpdated(city: String) {
        fragment2?.onCityUpdated(city)
    }
}