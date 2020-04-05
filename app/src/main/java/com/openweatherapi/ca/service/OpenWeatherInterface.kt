package com.openweatherapi.ca.service

import com.openweatherapi.ca.model.OpenWeatherResponse
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface OpenWeatherInterface {
    @GET("data/2.5/weather?")
    fun getWeatherByCity(
        @Query("q") city: String,
        @Query("APPID") appId: String,
        @Query("units") units: String = "metric"
    ): Call<OpenWeatherResponse>

    @GET("img/wn/{imageName}@2x.png")
    fun getWeatherIcon(
        @Path("imageName") imageName: String
    ): Call<ResponseBody>
}