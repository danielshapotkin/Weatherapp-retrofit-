package com.example.weather.presentation.thirdscreen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.weather.R

class ThirdScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_third_screen)

        val fragmentTransaction = supportFragmentManager.beginTransaction()

        val fragment1 = Fragment1()
        fragmentTransaction.replace(R.id.fragment_container1, fragment1)

        val fragment2 = Fragment2()
        fragmentTransaction.replace(R.id.fragment_container2, fragment2)

        fragmentTransaction.commit()



    }
}