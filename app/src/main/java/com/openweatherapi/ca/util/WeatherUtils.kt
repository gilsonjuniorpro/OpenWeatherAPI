package com.openweatherapi.ca.util

class WeatherUtils {
    companion object{
        fun converterMeterForSecondsToKmForHours(speed: Double): String {
            return (speed * 3.6).toInt().toString()
        }

    }
}