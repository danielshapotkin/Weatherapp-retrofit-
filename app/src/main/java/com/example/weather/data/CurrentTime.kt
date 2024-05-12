package com.example.weather.data

import com.example.weather.domain.ICurrentTime
import java.util.Calendar

class CurrentTime: ICurrentTime {
    override fun getCurrentTime():String {
        val calendar = Calendar.getInstance()
        val currentHour = calendar.get(Calendar.HOUR_OF_DAY)
        val currentMinute = calendar.get(Calendar.MINUTE)
        val currentSecond = calendar.get(Calendar.SECOND)
        return "${currentHour} : ${currentMinute} : ${currentSecond}"
    }
}