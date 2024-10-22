package com.example.weatherapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.models.weatherApp
import com.example.weatherapp.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository
) : ViewModel() {

    private val _weatherData = MutableLiveData<weatherApp?>()
    val weatherData: LiveData<weatherApp?> get() = _weatherData

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    // جلب بيانات الطقس
    fun fetchWeather(cityName: String, apiKey: String) {
        // تحقق مما إذا كانت البيانات موجودة بالفعل في الـ LiveData
        if (_weatherData.value != null && _weatherData.value?.name == cityName) {
            return
        }

        viewModelScope.launch(Dispatchers.IO) {
            try {
                // جلب البيانات المخزنة من Room
                val weatherFromDb = weatherRepository.getWeatherData(cityName)

                // التعامل مع البيانات المجمعة من Room
                weatherFromDb
                    .distinctUntilChanged()
                    .collect { dbData ->
                        if (dbData != null) {
                            Log.d("WeatherViewModel", "Data fetched from Room: ${dbData.name}")
                            _weatherData.postValue(dbData)
                        } else {
                            fetchFromApi(cityName, apiKey)
                        }
                    }
            } catch (e: Exception) {
                _errorMessage.postValue("Exception: ${e.message}")
                Log.e("WeatherViewModel", "Error: ${e.message}")
            }
        }
    }

    private suspend fun fetchFromApi(cityName: String, apiKey: String) {
        val weatherFromApi = weatherRepository.fetchWeatherFromApi(cityName, apiKey)
        if (weatherFromApi != null) {
            Log.d("WeatherViewModel", "Data fetched from API: ${weatherFromApi.name}")
            _weatherData.postValue(weatherFromApi)
        } else {
            _errorMessage.postValue("Failed to fetch weather data from API.")
        }
    }
}
