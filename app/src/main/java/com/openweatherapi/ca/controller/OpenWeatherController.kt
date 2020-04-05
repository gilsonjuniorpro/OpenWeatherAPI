package com.openweatherapi.ca.controller

import com.openweatherapi.ca.model.OpenWeatherResponse
import com.openweatherapi.ca.service.OpenWeatherInterface
import com.openweatherapi.ca.util.Constants
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class OpenWeatherController {
    companion object{
        fun getRetrofit(): OpenWeatherInterface? {
            val retrofit = Retrofit.Builder().baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            val service = retrofit.create(OpenWeatherInterface::class.java)

            return service
        }

        fun getWeatherCall(city: String): Call<OpenWeatherResponse>? {
            val call = getRetrofit()?.getWeatherByCity(city, Constants.API_KEY, "metric")

            return call
        }
    }
}