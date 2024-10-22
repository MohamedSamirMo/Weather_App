package com.example.weatherapp.repository

import android.util.Log
import com.example.weatherapp.models.weatherApp
import com.example.weatherapp.network.ApiInterface
import com.example.weatherapp.roomdb.MyDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class WeatherRepository @Inject constructor(
    private val api: ApiInterface,
    private val weatherDao: MyDao
) {

    // جلب بيانات الطقس من API وتحديث Room
    suspend fun fetchWeatherFromApi(cityName: String, apiKey: String): weatherApp? {
        // إجراء طلب API للحصول على بيانات الطقس
        val response = api.getWeatherData(cityName, apiKey, "metric")

        // طباعة كود الاستجابة للتأكد من نجاح الطلب أو فشله
        Log.d("WeatherRepository", "API Response Code: ${response.code()}")

        if (response.isSuccessful) {
            Log.d("WeatherRepository", "API Response Success: ${response.body()}")

            response.body()?.let { weatherResponse ->
                val weatherEntity = weatherApp(
                    base = weatherResponse.base,
                    clouds = weatherResponse.clouds,
                    cod = weatherResponse.cod,
                    coord = weatherResponse.coord,
                    dt = weatherResponse.dt,
                    id = weatherResponse.id,
                    main = weatherResponse.main,
                    name = weatherResponse.name,
                    sys = weatherResponse.sys,
                    timezone = weatherResponse.timezone,
                    visibility = weatherResponse.visibility,
                    weather = weatherResponse.weather,
                    wind = weatherResponse.wind
                )
                // حفظ البيانات في Room
                weatherDao.insertWeatherData(weatherEntity)
                return weatherEntity
            }
        } else {
            // في حال عدم نجاح الطلب، طباعة رسالة الخطأ
            Log.e("WeatherRepository", "API Error: ${response.message()}")
        }
        return null
    }

    // الحصول على بيانات الطقس المخزنة من Room
    fun getWeatherData(cityName: String): Flow<weatherApp?> {
        return weatherDao.getWeatherByCity(cityName)
    }

    // الحصول على بيانات الطقس من قاعدة البيانات أولاً، وإذا لم تكن موجودة جلبها من API
    suspend fun getWeather(cityName: String, apiKey: String): weatherApp? {
        val weatherDataFromDb = weatherDao.getWeatherByCity(cityName).first()
        return if (weatherDataFromDb != null) {
            Log.d("WeatherRepository", "Data fetched from Room: ${weatherDataFromDb.name}")
            weatherDataFromDb
        } else {
            Log.d("WeatherRepository", "No data in Room, fetching from API")
            fetchWeatherFromApi(cityName, apiKey)
        }
    }
}
