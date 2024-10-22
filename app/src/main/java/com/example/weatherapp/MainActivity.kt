package com.example.weatherapp

import android.os.Bundle
import android.widget.SearchView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.weatherapp.databinding.ActivityMainBinding
import com.example.weatherapp.models.weatherApp
import com.example.weatherapp.viewmodel.WeatherViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private val viewModel: WeatherViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // Fetch default city weather data
        fetchWeatherData("Jaipur")
        searchCity()

        // Observe the weather data from ViewModel
        viewModel.weatherData.observe(this, Observer { weather ->
            weather?.let {
                displayWeatherData(it)
            }
        })
    }

    private fun searchCity() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    fetchWeatherData(it)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })
    }

    private fun fetchWeatherData(cityName: String) {
        val apiKey = "491d5000961c37165ce758220c8fc812" // ضع مفتاح API الصحيح هنا
        viewModel.fetchWeather(cityName, apiKey)
    }

    private fun displayWeatherData(weather: weatherApp) {
        binding.temp.text = "${weather.main.temp} °C"
        binding.weather.text = weather.weather.firstOrNull()?.main ?: "Unknown"
        binding.max.text = "Max Temp: ${weather.main.temp_max} °C"
        binding.min.text = "Min Temp: ${weather.main.temp_min} °C"
        binding.humidity.text = "${weather.main.humidity} %"
        binding.wind.text = "${weather.wind.speed} m/s"
        binding.sunset.text = time(weather.sys.sunset.toLong())
        binding.sunRise.text = time(weather.sys.sunrise.toLong())
        binding.sea.text = "${weather.main.pressure} hPa"
        binding.Conditions.text = weather.weather.firstOrNull()?.main ?: "Unknown"
        binding.day.text = dayName(System.currentTimeMillis())
        binding.data.text = data()
        binding.cityName.text = "  "+weather.name

        ChangeImageDataWeather(weather.weather.firstOrNull()?.main ?: "Unknown")
    }

    private fun ChangeImageDataWeather(condition: String) {
        when (condition) {
            "Clear", "Sunny" -> {
                binding.root.setBackgroundResource(R.drawable.sunny_background)
                binding.lottieAnimationView.setAnimation(R.raw.sun)
            }
            "Clouds", "Overcast", "Mist", "Fog" -> {
                binding.root.setBackgroundResource(R.drawable.colud_background)
                binding.lottieAnimationView.setAnimation(R.raw.cloud)
            }
            "Rain", "Drizzle", "Showers" -> {
                binding.root.setBackgroundResource(R.drawable.rain_background)
                binding.lottieAnimationView.setAnimation(R.raw.rain)
            }
            "Snow", "Blizzard", "Hail" -> {
                binding.root.setBackgroundResource(R.drawable.snow_background)
                binding.lottieAnimationView.setAnimation(R.raw.snow)
            }
            else -> {
                binding.root.setBackgroundResource(R.drawable.sunny_background)
                binding.lottieAnimationView.setAnimation(R.raw.sun)
            }
        }
        binding.lottieAnimationView.playAnimation()
    }

    private fun data(): String {
        val sdf = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
        return sdf.format(Date())
    }

    private fun time(timestamp: Long): String {
        val sdf = SimpleDateFormat("HH:mm", Locale.getDefault())
        return sdf.format(Date(timestamp * 1000))
    }

    private fun dayName(timestamp: Long): String {
        val sdf = SimpleDateFormat("EEEE", Locale.getDefault())
        return sdf.format(Date())
    }
}
