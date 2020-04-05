package com.openweatherapi.ca.ui

import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import coil.api.load
import com.jarvis.ca.Mark
import com.openweatherapi.ca.R
import com.openweatherapi.ca.controller.OpenWeatherController
import com.openweatherapi.ca.databinding.ActivityMainBinding
import com.openweatherapi.ca.model.OpenWeatherResponse
import com.openweatherapi.ca.util.Constants
import com.openweatherapi.ca.util.WeatherUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.btSearch.setOnClickListener{ getWeather() }
    }

    private fun getWeather() {
        val city:String = binding.etCityName.text.toString()
        if(city.isNotEmpty()){
            val call = OpenWeatherController.getWeatherCall(city)

            call?.enqueue(object : Callback<OpenWeatherResponse> {
                override fun onFailure(call: Call<OpenWeatherResponse>?, t: Throwable?) {
                    Mark.showAlertError(this@MainActivity, "error ${t.toString()}")
                }

                override fun onResponse(
                    call: Call<OpenWeatherResponse>?,
                    response: Response<OpenWeatherResponse>?
                ) {
                    val weather = response?.body()!!
                    setVisibility()
                    closeKeyBoard()
                    binding.tvCity.text = weather.name
                    binding.tvSituation.text = weather.weather[0].main
                    binding.tvDescription.text = weather.weather[0].description
                    binding.tvMax.text = getString(R.string.temperature_max, weather.main.temp_max.toInt().toString())
                    binding.tvMin.text = getString(R.string.temperature_min, weather.main.temp_min.toInt().toString())
                    binding.tvTemperature.text = getString(R.string.temperature, weather.main.temp.toInt().toString())
                    binding.tvFeelsLike.text = getString(R.string.feels_like, weather.main.feels_like.toInt().toString())
                    val inKmPerHour = WeatherUtils.converterMeterForSecondsToKmForHours(weather.wind.speed)
                    binding.tvWindSpeed.text = getString(R.string.wind, inKmPerHour)

                    getWeatherIcon(weather.weather[0].icon)
                }
            })
        }else{
            Mark.showAlertError(this@MainActivity, getString(R.string.city_is_empty))
        }
    }

    private fun getWeatherIcon(icon: String){
        if(!TextUtils.isEmpty(icon)) {
            binding.ivIcon.visibility = View.VISIBLE
            binding.ivIcon.load(Constants.BASE_URL_ICON + "${icon}@2x.png") {
                crossfade(true)
                crossfade(1000)
            }
        }
    }

    private fun setVisibility(){
        binding.tvCity.visibility = View.VISIBLE
        binding.tvSituation.visibility = View.VISIBLE
        binding.tvDescription.visibility = View.VISIBLE
        binding.tvMax.visibility = View.VISIBLE
        binding.tvMin.visibility = View.VISIBLE
        binding.tvTemperature.visibility = View.VISIBLE
        binding.tvFeelsLike.visibility = View.VISIBLE
        binding.tvWindSpeed.visibility = View.VISIBLE
    }

    private fun closeKeyBoard() {
        val view = this.currentFocus
        if (view != null) {
            val imm: InputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

}