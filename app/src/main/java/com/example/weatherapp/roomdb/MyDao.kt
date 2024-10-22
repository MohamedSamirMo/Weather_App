package com.example.weatherapp.roomdb

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

import com.example.weatherapp.models.weatherApp

@Dao
interface MyDao {

    // إدخال بيانات الطقس في قاعدة البيانات
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWeatherData(weatherData: weatherApp)

    // الحصول على جميع بيانات الطقس
    @Query("SELECT * FROM WeatherApp")
    fun getAllWeatherData(): Flow<List<weatherApp>>

    // الحصول على بيانات الطقس لمدينة معينة باستخدام Flow
    @Query("SELECT * FROM WeatherApp WHERE name = :cityName ")
    fun getWeatherByCity(cityName: String): Flow<weatherApp?>
}
